-- ./kong/plugins/auth-check/schema.lua
return {
  name = "auth-check",
  fields = {
    { config = {
        type = "record",
        fields = {
          { auth_service_url = { type = "string", required = true }, },
          { cache_ttl = { type = "integer", default = 300 }, },
          { ssl_verify = { type = "boolean", default = false }, },
        },
      },
    },
  },
}
