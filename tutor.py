from flask import *
from database import *
import uuid
tutor=Blueprint('tutor',__name__)

@tutor.route("tutor_home")
def tutor_home():
    return render_template("tutor_home.html")


@tutor.route("tutor_view_profile",methods=['get','post'])
def tutor_view_profile():
    data={}
    lid=session['login_id']
    q="SELECT * FROM `tutors` INNER JOIN `login` USING(`login_id`) WHERE `login_id`='%s'"%(lid)
    res=select(q)
    if res:
        data['my_profile']=res
        
    if 'updateprofile' in request.form:
        fname=request.form['fname']
        lname=request.form['lname']
        place=request.form['place']
        phone=request.form['phone']
        email=request.form['email']
        uname=request.form['uname']
        passw=request.form['passw']
        q="UPDATE `login` SET `username`='%s',`password`='%s' WHERE `login_id`='%s'"%(uname,passw,lid)
        update(q)
        q1="UPDATE `tutors` SET `firstname`='%s',`lastname`='%s',`place`='%s',`phone`='%s',`email`='%s' WHERE `login_id`='%s'"%(fname,lname,place,phone,email,lid)
        update(q1)
        flash("Successfully Updated")
        return redirect(url_for("tutor.tutor_view_profile"))
    return render_template("tutor_view_profile.html",data=data)

@tutor.route("tutor_view_assigned_course")
def tutor_view_assigned_course():
    data={}
    tid=session['tutor_id']
    q="SELECT * FROM `course` INNER JOIN `assigntocourse` USING(`course_id`) WHERE tutor_id='%s'"%(tid)
    res=select(q)
    data['view_course']=res
    return render_template("tutor_view_assigned_course.html",data=data)


@tutor.route("tutor_view_syllabus")
def tutor_view_syllabus():
    data={}
    course_id=request.args['course_id']
    q="SELECT * FROM `syllabus` WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    data['view_syllabus']=res
    
    return render_template("tutor_view_syllabus.html",data=data)


@tutor.route("tutor_send_complaints",methods=['get','post'])
def tutor_send_complaints():
    data={}
    lid=session['login_id']
    q="SELECT *,CONCAT(`managers`.`firstname`,' ',`managers`.`lastname`) AS manager_name,`managers`.`login_id` AS mlid FROM `tutors` INNER JOIN `managers` USING(`manager_id`) WHERE `tutors`.`login_id`='%s'"%(lid)
    res=select(q)
    mlid=res[0]['mlid']
    data['view_manager']=res
    
    if 'comp' in request.form:
        cmp=request.form['cmp']
        q2="INSERT INTO  `complaint` VALUES(NULL,'%s','%s','Pending',CURDATE())"%(lid,cmp)
        insert(q2)
        flash("Successfully Send")
        return redirect(url_for("tutor.tutor_send_complaints"))
    
    q="SELECT * FROM `complaint` WHERE `sender_id`='%s'"%(lid)
    ress=select(q)
    data['view_complaints']=ress
        
    return render_template("tutor_send_complaints.html",data=data)


@tutor.route("tutor_add_class",methods=['get','post'])
def tutor_add_class():
    data={}
    course_id=request.args['course_id']
  
    if 'class_video' in request.form:
        video=request.files['video']
        details=request.form['details']
        
        path='static/uploads/videos/'+str(uuid.uuid4())+video.filename
        video.save(path)
        
        q2="INSERT INTO `videos` VALUES(NULL,'%s','%s','%s',CURDATE())"%(course_id,details,path)
        insert(q2)
        flash("Successfully Add")
        return redirect(url_for("tutor.tutor_add_class",course_id=course_id))
    
    q="SELECT * FROM `videos` WHERE `course_id`='%s'"%(course_id)
    ress=select(q)
    data['view_class']=ress
        
    return render_template("tutor_add_class.html",data=data)


@tutor.route("tutor_view_students",methods=['get','post'])
def tutor_view_students():
    data={}
    course_id=request.args['course_id']
    
    q="SELECT * FROM `payment` INNER JOIN `request` USING(`request_id`) INNER JOIN `student` USING(`student_id`) WHERE `course_id`='%s'"%(course_id)
    ress=select(q)
    data['view_students']=ress
        
    return render_template("tutor_view_students.html",data=data)
