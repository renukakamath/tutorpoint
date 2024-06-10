from flask import *
from database import *
manager=Blueprint('manager',__name__)

@manager.route("manager_home")
def manager_home():
    return render_template("manager_home.html")


@manager.route("manager_manage_course",methods=['get','post'])
def manager_manage_course():
    data={}
    manager_id=session['manager_id']
    if 'addcourse' in request.form:
        coursename=request.form['coursename']
        rate=request.form['rate']
        duration=request.form['duration']
        q="INSERT INTO `course` VALUES(NULL,'%s','%s','%s','%s')"%(manager_id,coursename,rate,duration)
        insert(q)
        flash("Successfully Added")
        return redirect(url_for("manager.manager_manage_course"))
    
    q="SELECT * FROM `course` WHERE `manager_id`='%s'"%(manager_id)
    res=select(q)
    data['view_course']=res
    
    if 'action' in request.args:
        action=request.args['action']
        course_id=request.args['course_id']
    else:
        action=None
    if action=="edit":
        q="SELECT * FROM `course` WHERE `course_id`='%s'"%(course_id)
        res=select(q)
        data['edit_course']=res
    if action=="remove":
        q="DELETE FROM `course` WHERE `course_id`='%s'"%(course_id)
        delete(q)
        flash("Successfully Removed")
        return redirect(url_for("manager.manager_manage_course"))
    if 'editcourse' in request.form:
        coursename=request.form['coursename']
        rate=request.form['rate']
        duration=request.form['duration']
        q="UPDATE `course` SET `course`='%s',`rate`='%s',`duration`='%s' WHERE `course_id`='%s'"%(coursename,rate,duration,course_id)
        update(q)
        flash("Successfully Updated")
        return redirect(url_for("manager.manager_manage_course"))
        
    return render_template("manager_manage_course.html",data=data)



@manager.route("manager_manage_syllabus",methods=['get','post'])
def manager_manage_syllabus():
    data={}
    course_id=request.args['course_id']
    data['course_id']=course_id
    duration=request.args['duration']
    data['duration']=duration
    
    if 'addsyllabus' in request.form:
        syllabus=request.form['syllabus']
        timetaken=request.form['timetaken']
        btime=request.form['btime']
        if float(timetaken)<=float(btime):
            q="INSERT INTO `syllabus` VALUES(NULL,'%s','%s','%s')"%(course_id,syllabus,timetaken)
            insert(q)
            flash("Successfully Added")
        else:
            flash("Time Taken Is Higher Than Course Duration")
    
            
        return redirect(url_for("manager.manager_manage_syllabus",course_id=course_id,duration=duration))
    
    q="SELECT * FROM `syllabus` WHERE `course_id`='%s'"%(course_id)
    res=select(q)
    
    if res:
        data['view_syllabus']=res
        qw="SELECT *,sum(timetaken) as ttime FROM `syllabus` WHERE `course_id`='%s'"%(course_id)
        resw=select(qw)
        if resw:
            ttime=resw[0]['ttime']
            if ttime:
                btime=float(duration)-float(ttime)
                data['btime']=btime
    else:
        data['btime']=duration
        
    
    
    if 'action' in request.args:
        action=request.args['action']
        syllabus_id=request.args['syllabus_id']
        course_id=request.args['course_id']
    else:
        action=None
    if action=="edit":
        q="SELECT * FROM `syllabus` WHERE `syllabus_id`='%s'"%(syllabus_id)
        res=select(q)
        data['edit_syllabus']=res
    if action=="remove":
        q="DELETE FROM `syllabus` WHERE `syllabus_id`='%s'"%(syllabus_id)
        delete(q)
        flash("Successfully Removed")
        return redirect(url_for("manager.manager_manage_syllabus",course_id=course_id,duration=duration))
    if 'editsyllabus' in request.form:
        syllabus=request.form['syllabus']
        timetaken=request.form['timetaken']
        btime=request.form['btime']
        if float(timetaken)<=float(btime):
            q="UPDATE `syllabus` SET `syllabus`='%s',`timetaken`='%s' WHERE `syllabus_id`='%s'"%(syllabus,timetaken,syllabus_id)
            update(q)
            flash("Successfully Updated")
            return redirect(url_for("manager.manager_manage_syllabus",course_id=course_id,duration=duration))
        else:
            flash("Time Taken Is Higher Than Course Duration")
            return redirect(url_for("manager.manager_manage_syllabus",course_id=course_id,duration=duration))
    return render_template("manager_manage_syllabus.html",data=data)


@manager.route("manager_manage_tutors",methods=['get','post'])
def manager_manage_tutors():
    data={}
    manager_id=session['manager_id']
    if 'addtutor' in request.form:
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
            return redirect(url_for("manager.manager_manage_tutors"))
        else:
            q="INSERT INTO `login` VALUES(NULL,'%s','%s','Tutor')"%(uname,passw)
            id=insert(q)
            q1="INSERT INTO `tutors` VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s')"%(id,manager_id,fname,lname,place,phone,email)
            insert(q1)
            flash("Regsitration Successfully Completed.")
            return redirect(url_for("manager.manager_manage_tutors"))
    
    q="SELECT * FROM `tutors` INNER JOIN `login` USING(`login_id`) WHERE `manager_id`='%s'"%(manager_id)
    res=select(q)
    data['view_tutors']=res
    
    if 'action' in request.args:
        action=request.args['action']
        tlid=request.args['tlid']
    else:
        action=None
    
    if action=="inactive":
        q="UPDATE `login` SET `usertype`='In-Active' WHERE `login_id`='%s'"%(tlid)
        update(q)
        flash("In-Aactivated")
        return redirect(url_for("manager.manager_manage_tutors"))
    if action=="active":
        q="UPDATE `login` SET `usertype`='Tutor' WHERE `login_id`='%s'"%(tlid)
        update(q)
        flash("Aactivated")
        return redirect(url_for("manager.manager_manage_tutors"))
   
    return render_template("manager_manage_tutors.html",data=data)



@manager.route("manager_assign_course_to_tutor",methods=['get','post'])
def manager_assign_course_to_tutor():
    data={}
    manager_id=session['manager_id']
    tutor_id=request.args['tutor_id']
    data['tutor_id']=tutor_id
    q="SELECT * FROM `course` WHERE `manager_id`='%s' AND `course_id` NOT IN ( SELECT `course_id` FROM `assigntocourse`  )"%(manager_id)
    print(q)
    res=select(q)
    data['view_course']=res
    
    q1="SELECT * FROM `assigntocourse` INNER JOIN `course` USING(`course_id`) WHERE `tutor_id`='%s'"%(tutor_id)
    res1=select(q1)
    data['view_assigned']=res1
    
    if 'assign' in request.form:
        course_id=request.form['course_id']
        qa="INSERT INTO `assigntocourse` VALUES(NULL,'%s','%s',CURDATE())"%(course_id,tutor_id)
        insert(qa)
        flash('Successfully Assigned')
        return redirect(url_for("manager.manager_assign_course_to_tutor",tutor_id=tutor_id))
    
    if 'action' in request.args:
        action=request.args['action']
        assigncourse_id=request.args['assigncourse_id']
    else:
        action=None
    if action=="remove":
        qr="DELETE FROM `assigntocourse` WHERE `assigncourse_id`='%s'"%(assigncourse_id)
        delete(qr)
        flash("Successfully Removed")
        return redirect(url_for("manager.manager_assign_course_to_tutor",tutor_id=tutor_id))
    
    return render_template("manager_assign_course_to_tutor.html",data=data)



@manager.route("manager_view_request",methods=['get','post'])
def manager_view_request():
    data={}
    manager_id=session['manager_id']
    q="SELECT * FROM `request` INNER JOIN `course` USING(`course_id`) WHERE `manager_id`='%s'"%(manager_id)
    res=select(q)
    data['view_my_request']=res
    
    if 'action' in request.args:
        action=request.args['action']
        request_id=request.args['request_id']
    else:
        action=None
    if action=="accept":
        qr="UPDATE `request` SET `status`='Accepted' WHERE `request_id`='%s'"%(request_id)
        update(qr)
        flash("Successfully Accepted")
        return redirect(url_for("manager.manager_view_request"))
    if action=="reject":
        qr="UPDATE `request` SET `status`='Rejected' WHERE `request_id`='%s'"%(request_id)
        update(qr)
        flash("Successfully Rejected")
        return redirect(url_for("manager.manager_view_request"))
    
    return render_template("manager_view_request.html",data=data)



@manager.route("manager_view_payment",methods=['get','post'])
def manager_view_payment():
    data={}
    request_id=request.args['request_id']
    q="SELECT *,CONCAT(`firstname`,' ',`lastname`) AS student_name FROM `payment` INNER JOIN `request` USING(`request_id`) INNER JOIN `student` USING(`student_id`) WHERE `request_id`='%s'"%(request_id)
    res=select(q)
    data['view_payment']=res
    
    return render_template("manager_view_payment.html",data=data)


@manager.route("manager_view_complaints",methods=['get','post'])
def manager_view_complaints():
    data={}
    mlid=session['login_id']
    q="SELECT * FROM `complaint` INNER JOIN `tutors` ON `tutors`.`login_id`=`complaint`.`sender_id`"
    res=select(q)
    data['view_tutor_complaints']=res
    
    q1="SELECT * FROM `complaint` INNER JOIN `student` ON `student`.`login_id`=`complaint`.`sender_id`"
    ress=select(q1)
    data['view_student_complaints']=ress
    
    j=0
    for i in range(1,len(res)+1):
        if 'replys'+str(i) in request.form:
            reply=request.form['reply'+str(i)]
            print(res[j]['complaint_id'])
            q="update complaint set reply='%s' where complaint_id='%s'"%(reply,res[j]['complaint_id'])
            print(q)
            update(q)
            return redirect(url_for('manager.manager_view_complaints'))
        j=j+1
	
    k=0
    for i in range(1,len(ress)+1):
        if 'sreplys'+str(i) in request.form:
            reply=request.form['sreply'+str(i)]
            print(ress[k]['complaint_id'])
            q="update complaint set reply='%s' where complaint_id='%s'"%(reply,ress[k]['complaint_id'])
            print(q)
            update(q)
            return redirect(url_for('manager.manager_view_complaints'))
        k=k+1
    
    return render_template("manager_view_complaints.html",data=data)
