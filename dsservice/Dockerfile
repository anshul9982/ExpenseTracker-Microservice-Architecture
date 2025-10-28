# Dockerfiles/dsservice.Dockerfile
FROM python:3.11-slim
WORKDIR /app

COPY source/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY source/ .

EXPOSE 5000
CMD ["python", "-m", "app.utilis"]
