-- ./kong/plugins/auth-check/handler.lua (NO CACHE - FULL LOGGING)
local http = require "resty.http"
local cjson = require "cjson.safe"

local AuthHandler = {
  PRIORITY = 1000,
  VERSION = "1.2.0",
}

function AuthHandler:access(conf)
  local service = kong.router.get_service()
  if service then
    kong.log.info("=== AUTH-CHECK for service: ", service.name, " ===")
  else
    kong.log.info("=== AUTH-CHECK PLUGIN START ===")
  end
  
  local auth_header = kong.request.get_header("authorization")
  kong.log.info("Step 1: Checking auth header")
  
  if not auth_header then
    kong.log.err("ERROR: Missing Authorization header")
    return kong.response.exit(401, { message = "Missing Authorization header" })
  end
  
  kong.log.info("Step 2: Auth header present: ", string.sub(auth_header, 1, 30))

  local token = string.match(auth_header, "Bearer%s+(.+)")
  if not token then
    kong.log.err("ERROR: Invalid Authorization header format")
    return kong.response.exit(401, { message = "Invalid Authorization header format" })
  end
  
  kong.log.info("Step 3: Token extracted, length: ", string.len(token))

  kong.service.request.clear_header("X-Claim-Userid")
  kong.log.info("Step 4: Cleared X-Claim-Userid header")

  -- NO CACHE - Direct call every time
  kong.log.info("Step 5: Calling auth service at: ", conf.auth_service_url .. "/auth/v1/ping")
  
  local httpc = http.new()
  httpc:set_timeout(5000) -- 5 second timeout
  
  local res, http_err = httpc:request_uri(conf.auth_service_url .. "/auth/v1/ping", {
    method = "GET",
    headers = { 
      ["Authorization"] = "Bearer " .. token,
      ["Content-Type"] = "application/json"
    },
    ssl_verify = conf.ssl_verify
  })

  if not res then
    kong.log.err("Step 6: FAILED - Auth Service unreachable: ", http_err or "unknown error")
    return kong.response.exit(503, { message = "Auth service unavailable: " .. (http_err or "unknown") })
  end

  kong.log.info("Step 6: Got response from auth service")
  kong.log.info("Response status: ", res.status)
  kong.log.info("Response body: ", res.body or "empty")
  kong.log.info("Response headers: ", cjson.encode(res.headers or {}))

  if res.status ~= 200 then
    kong.log.err("Step 7: FAILED - Token rejected by auth service, status: ", res.status)
    return kong.response.exit(401, { message = "Invalid token - status " .. res.status })
  end

  kong.log.info("Step 7: Auth service returned 200 OK")

  local body, decode_err = cjson.decode(res.body)
  if not body then
    kong.log.err("Step 8: FAILED - JSON decode error: ", decode_err or "unknown")
    kong.log.err("Raw body was: ", res.body)
    return kong.response.exit(500, { message = "Invalid response format from auth service" })
  end

  kong.log.info("Step 8: JSON decoded successfully")
  kong.log.info("Decoded body: ", cjson.encode(body))

  if not body.userId then
    kong.log.err("Step 9: FAILED - Missing userId in response")
    kong.log.err("Available keys in body: ", cjson.encode(body))
    return kong.response.exit(500, { message = "Auth service response missing userId" })
  end

  kong.log.info("Step 9: Found userId: ", body.userId)
  kong.service.request.set_header("X-Claim-Userid", body.userId)
  kong.log.info("Step 10: SUCCESS - Set X-Claim-Userid header")
  kong.log.info("=== AUTH-CHECK COMPLETE - FORWARDING TO SERVICE ===")
end

return AuthHandler