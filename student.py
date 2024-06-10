from asyncore import read
from flask import *
from database import *
student=Blueprint('student',__name__)

@student.route("student_home",methods=['get','post'])
def student_home():
    data={}
    sid=session['student_id']
    q="SELECT * FROM `course` "
    res=select(q)
    data['view_course']=res
    
    if 'action' in request.args:
        action=request.args['action']
        course_id=request.args['course_id']
    else:
        action=None
    
    if action=="request":
        q="SELECT * FROM `request` WHERE `course_id`='%s' AND `student_id`='%s' AND `status`!='Rejected'"%(course_id,sid)
        res=select(q)
        if res:
            flash("Already Requested")
            return redirect(url_for("student.student_home"))
        else:
            q1="INSERT INTO `request` VALUES(NULL,'%s','%s',CURDATE(),'Pending')"%(course_id,sid)
            insert(q1)
            flash("Successfully Requested")
            return redirect(url_for("student.student_home"))
    
    
    return render_template("student_home.html",data=data)

@student.route("student_view_courses",methods=['get','post'])
def student_view_courses():
    data={}
    sid=session['student_id']
    q="SELECT * FROM `course` "
    res=select(q)
    data['view_course']=res
    
    if 'action' in request.args:
        action=request.args['action']
        course_id=request.args['course_id']
    else:
        action=None
    
    if action=="request":
        q="SELECT * FROM `request` WHERE `course_id`='%s' AND `student_id`='%s' AND `status`!='Rejected'"%(course_id,sid)
        res=select(q)
        if res:
            flash("Already Requested")
            return redirect(url_for("student.student_view_courses"))
        else:
            q1="INSERT INTO `request` VALUES(NULL,'%s','%s',CURDATE(),'Pending')"%(course_id,sid)
            insert(q1)
            flash("Successfully Requested")
            return redirect(url_for("student.student_view_my_request"))
    
    
    return render_template("student_view_courses.html",data=data)


@student.route("student_view_syllabus")
def student_view_syllabus():
    data={}
    course_id=request.args['course_id']
    q="SELECT * FROM `syllabus` WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    data['view_syllabus']=res
    
    return render_template("student_view_syllabus.html",data=data)


@student.route("student_view_my_request")
def student_view_my_request():
    data={}
    sid=session['student_id']
    q="SELECT * FROM `request` INNER JOIN `course` USING(`course_id`) WHERE `student_id`='%s'"%(sid)
    res=select(q)
    data['view_my_request']=res
    
    return render_template("student_view_my_request.html",data=data)


@student.route("student_make_payment",methods=['get','post'])
def student_make_payment():
    data={}
    amount=request.args['amount']
    request_id=request.args['request_id']
    data['amount']=amount
    
    if 'pay' in request.form:
        b_amount=request.form['b_amount']
        q="INSERT INTO `payment` VALUES(NULL,'%s','%s',CURDATE())"%(request_id,b_amount)
        insert(q)
        q1="UPDATE `request` SET `status`='Paid' WHERE `request_id`='%s'"%(request_id)
        update(q1)
        flash("Payment Successfully Completed")
        return redirect(url_for("student.student_view_my_request"))
        
        
    return render_template("student_make_payment.html",data=data)

@student.route("student_view_class",methods=['get','post'])
def student_view_class():
    data={}
    course_id=request.args['course_id']
    
    q="SELECT * FROM `videos` WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    data['view_course_videos']=res
        
    return render_template("student_view_class.html",data=data)



@student.route("student_view_tutor_details",methods=['get','post'])
def student_view_tutor_details():
    data={}
    course_id=request.args['course_id']
    
    q="SELECT * FROM `assigntocourse` INNER JOIN `tutors` USING(`tutor_id`) WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    data['view_tutor']=res
        
    return render_template("student_view_tutor_details.html",data=data)



@student.route("student_send_complaints",methods=['get','post'])
def student_send_complaints():
    data={}
    lid=session['login_id']
    manager_id=request.args['manager_id']
    
    qw="select *,concat(`firstname`,' ',`lastname`) as manager_name from `managers` where `manager_id`='%s'"%(manager_id)
    res=select(qw)
    data['view_manager']=res
    
   
    
    if 'comp' in request.form:
        cmp=request.form['cmp']
        q2="INSERT INTO  `complaint` VALUES(NULL,'%s',(SELECT `login_id` FROM `managers` WHERE `manager_id`='%s'),'%s','Pending',CURDATE())"%(lid,manager_id,cmp)
        insert(q2)
        flash("Successfully Send")
        return redirect(url_for("student.student_send_complaints",manager_id=manager_id))
    
    q="SELECT * FROM `complaint` WHERE `sender_id`='%s'"%(lid)
    ress=select(q)
    data['view_complaints']=ress
        
    return render_template("student_send_complaints.html",data=data)


@student.route("student_rate_app",methods=['get','post'])
def student_rate_app():
    data={}
    sid=session['student_id']
    if 'rate_app' in request.form:
        rate=request.form['rate']
        desp=request.form['desp']
        
        q1="SELECT * FROM `rating` WHERE `student_id`='%s'"%(sid)
        ress=select(q1)
        if ress:
            rating_id=ress[0]['rating_id']
            q2="UPDATE `rating` SET `rated`='%s',`details`='%s',`date`=CURDATE() WHERE `rating_id`='%s'"%(rate,desp,rating_id)
            update(q2)
            flash("Successfully Updated")
            return redirect(url_for("student.student_rate_app"))
        else:  
            q="INSERT INTO `rating` VALUES(NULL,'%s','%s','%s',CURDATE())"%(sid,rate,desp)
            insert(q)
            flash("Successfully Rated")
            return redirect(url_for("student.student_rate_app"))
    
    q1="SELECT * FROM `rating` WHERE `student_id`='%s'"%(sid)
    res=select(q1)
    data['view_ratings']=res
    
    return render_template("student_rate_app.html",data=data)