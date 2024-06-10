from flask import *
from database import *
import uuid
public=Blueprint('public',__name__)

@public.route("/")
def index():
    data={}
    q="SELECT * FROM `course` "
    res=select(q)
    data['view_course']=res
    
    q="SELECT * FROM `rating` INNER JOIN `student` USING(`student_id`)"
    res=select(q)
    data['view_rating']=res
    
    return render_template("index.html",data=data)

@public.route("/public_view_syllabus")
def public_view_syllabus():
    data={}
    course_id=request.args['course_id']
    q="SELECT * FROM `syllabus` WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    data['view_syllabus']=res
    
    return render_template("public_view_syllabus.html",data=data)

@public.route("/login",methods=['get','post'])
def login():
    data={}
    if 'login' in request.form:
        uname=request.form['uname']
        passw=request.form['passw']
        q="SELECT * FROM `login` WHERE `username`='%s' AND `password`='%s'"%(uname,passw)
        res=select(q)
        if res:
            session['login_id']=res[0]['login_id']
            lid=session['login_id']
            if res[0]['usertype']=="admin":
                flash("Welcome Back Admin")
                return redirect(url_for("admin.admin_home"))
            
            if res[0]['usertype']=="Pending":
                flash("Your Account Is Under Verification, Please Wait For The Confirmation.")
                return redirect(url_for("public.login"))
            
            if res[0]['usertype']=="Manager":
                q="SELECT * FROM `managers` WHERE `login_id`='%s'"%(lid)
                res1=select(q)
                if res1:
                    session['manager_id']=res1[0]['manager_id']
                    flash("Welcome Back Manager")
                    return redirect(url_for("manager.manager_home"))
            if res[0]['usertype']=="Tutor":
                q="SELECT * FROM `tutors` WHERE `login_id`='%s'"%(lid)
                res1=select(q)
                if res1:
                    session['tutor_id']=res1[0]['tutor_id']
                    flash("Welcome Back Tutor")
                    return redirect(url_for("tutor.tutor_home"))
            if res[0]['usertype']=="Student":
                q="SELECT * FROM `student` WHERE `login_id`='%s'"%(lid)
                res1=select(q)
                if res1:
                    session['student_id']=res1[0]['student_id']
                    flash("Welcome Back Student")
                    return redirect(url_for("student.student_home"))
        else:
            flash("Invalid Username Or Password")
            return redirect(url_for("public.login"))
            
            
    return render_template("login.html",data=data)


@public.route("/manager_registration",methods=['get','post'])
def manager_registration():
    data={}
    if 'manager' in request.form:
        fname=request.form['fname']
        lname=request.form['lname']
        place=request.form['place']
        phone=request.form['phone']
        email=request.form['email']
        uname=request.form['uname']
        passw=request.form['passw']
        img=request.files['img']
        path="static/uploads"+str(uuid.uuid4())+img.filename
        img.save(path)
        
        q="SELECT * FROM `login` WHERE `username`='%s'"%(uname)
        res=select(q)
        if res:
            flash("Username Already Exist. Please Choose Anotherone")
            return redirect(url_for("public.manager_registration"))
        else:
            q="INSERT INTO `login` VALUES(NULL,'%s','%s','Pending')"%(uname,passw)
            id=insert(q)
            q1="INSERT INTO `managers` VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email,path)
            insert(q1)
            flash("Regsitration Successfully Completed. Please Wait For The Confirmation To Login.")
            return redirect(url_for("public.login"))
    
    return render_template("manager_registration.html",data=data)



@public.route("/student_registration",methods=['get','post'])
def student_registration():
    data={}
    if 'student' in request.form:
        fname=request.form['fname']
        lname=request.form['lname']
        place=request.form['place']
        phone=request.form['phone']
        email=request.form['email']
        uname=request.form['uname']
        passw=request.form['passw']
        
        q="SELECT * FROM `login` WHERE `username`='%s'"%(uname)
        res=select(q)
        if res:
            flash("Username Already Exist. Please Choose Anotherone")
            return redirect(url_for("public.student_registration"))
        else:
            q="INSERT INTO `login` VALUES(NULL,'%s','%s','Student')"%(uname,passw)
            id=insert(q)
            q1="INSERT INTO `student` VALUES(NULL,'%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email)
            insert(q1)
            flash("Regsitration Successfully Completed.")
            return redirect(url_for("public.login"))
    
    return render_template("student_registration.html",data=data)