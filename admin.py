from flask import *
from database import *
admin=Blueprint('admin',__name__)

@admin.route("admin_home")
def admin_home():
    return render_template("admin_home.html")



@admin.route("admin_verify_manager")
def admin_verify_manager():
    data={}
    q="SELECT * FROM `managers` INNER JOIN `login` USING(`login_id`) WHERE `usertype`='Pending'"
    res=select(q)
    data['verify_manager']=res
    
    if 'action' in request.args:
        action=request.args['action']
        login_id=request.args['login_id']
    else:
        action=None
        
    if action=="accept":
        q="UPDATE `login` SET `usertype`='Manager' WHERE `login_id`='%s'"%(login_id)
        update(q)
        flash("Successfully Accepted.")
        return redirect(url_for("admin.admin_verify_manager"))
    
    if action=="reject":
        q="UPDATE `login` SET `usertype`='Reject' WHERE `login_id`='%s'"%(login_id)
        update(q)
        flash("Successfully Rejected.")
        return redirect(url_for("admin.admin_verify_manager"))
    
    return render_template("admin_verify_manager.html",data=data)



@admin.route("admin_view_manager")
def admin_view_manager():
    data={}
    q="SELECT * FROM `managers` INNER JOIN `login` USING(`login_id`) WHERE `usertype`='Manager'"
    res=select(q)
    data['manager']=res
    return render_template("admin_view_manager.html",data=data)



@admin.route("admin_view_tutors")
def admin_view_tutors():
    data={}
    manager_id=request.args['manager_id']
    q="SELECT * FROM `tutors` WHERE `manager_id`='%s'"%(manager_id)
    res=select(q)
    data['view_tutor']=res
    return render_template("admin_view_tutors.html",data=data)

@admin.route("admin_view_course")
def admin_view_course():
    data={}
    manager_id=request.args['manager_id']
    q="SELECT * FROM `course` WHERE `manager_id`='%s'"%(manager_id)
    res=select(q)
    data['view_course']=res
    return render_template("admin_view_course.html",data=data)


@admin.route("admin_view_syllabus")
def admin_view_syllabus():
    data={}
    course_id=request.args['course_id']
    q="SELECT * FROM `syllabus` WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    data['view_syllabus']=res
    return render_template("admin_view_syllabus.html",data=data)


@admin.route("admin_view_videos")
def admin_view_videos():
    data={}
    course_id=request.args['course_id']
    q="SELECT * FROM `videos` WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    data['view_videos']=res
    return render_template("admin_view_videos.html",data=data)


@admin.route("admin_view_students")
def admin_view_students():
    data={}
    course_id=request.args['course_id']
    q="SELECT *,`payment`.`date` AS pdate FROM `student` INNER JOIN `request` USING(`student_id`) INNER JOIN `payment` USING(`request_id`) WHERE `course_id`='%s' AND `status`='Paid'"%(course_id)
    res=select(q)
    data['view_students']=res
    return render_template("admin_view_students.html",data=data)


@admin.route("admin_view_rating")
def admin_view_rating():
    data={}
    q="SELECT * FROM `rating` INNER JOIN `student` USING(`student_id`)"
    res=select(q)
    data['view_rating']=res
    return render_template("admin_view_rating.html",data=data)
