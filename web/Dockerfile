FROM python:3.7-alpine
MAINTAINER August

ADD . /code
WORKDIR /code

RUN pip install -r requirements.txt
CMD ["python", "app.py"]
