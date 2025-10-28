import json
import os
from flask import Flask
import time
from flask import request, jsonify
from kafka import KafkaProducer
from dotenv import load_dotenv
from ..service.messageService import MessageService

# Load environment variables from the .env file in the parent 'app' directory
dotenv_path = os.path.join(os.path.dirname(__file__), '..', '.env')
load_dotenv(dotenv_path=dotenv_path)

app = Flask(__name__)
messageService = MessageService()

def create_kafka_producer():
    retries = 5
    for i in range(retries):
        try:
            producer = KafkaProducer(
                bootstrap_servers=['kafka:9092'],
                value_serializer=lambda v: v.encode('utf-8'),
                api_version_auto_timeout_ms=10000, # Increase timeout for initial connection
                reconnect_backoff_ms=2000 # Wait 2s before retrying connection
            )
            app.logger.info("Successfully connected to Kafka.")
            return producer
        except Exception as e:
            app.logger.error(f"Failed to connect to Kafka (attempt {i+1}/{retries}): {e}")
            if i < retries - 1:
                time.sleep(5) # Wait 5 seconds before the next attempt
    return None

producer = create_kafka_producer()

@app.route('/')
def hello_world():
    return 'Hello, World!'

@app.route('/ds/v1/message', methods=['POST'])
def handle_message():
    message = request.json.get('message')
    user_id = request.headers.get('X-Claim-Userid')  # Get userId from Kong header

    if not message:
        return jsonify({"error": "message field is required"}), 400
    if not user_id:
        return jsonify({"error": "X-Claim-Userid header is required"}), 400

    result = messageService.process_message(message)
    if producer and producer.bootstrap_connected():
        result_dict = result.dict()
        result_dict['user_id'] = user_id  # Add userId to the result
        serialized_result = json.dumps(result_dict)  # Serialize with userId
        producer.send(os.getenv("KAFKA_TOPIC"), serialized_result)
        producer.flush()

    result_dict = result.dict()
    result_dict['user_id'] = user_id
    return jsonify(result_dict)
