from flask import Flask
from public import public
from admin import admin
from manager import manager
from tutor import tutor
from api import api
# from student import student

app=Flask(__name__)
app.secret_key="1234"

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix="/admin")
app.register_blueprint(manager,url_prefix="/manager")
app.register_blueprint(tutor,url_prefix="/tutor")
app.register_blueprint(api,url_prefix="/api")
# app.register_blueprint(student,url_prefix="/student")

app.run(debug=True,port=5450,host="0.0.0.0")