from flask import * 
from database import* 
import uuid

api=Blueprint('api',__name__)

@api.route('/logins')
def logins():
	data={}
	u=request.args['username']
	p=request.args['password']
	q1="select * from login where username='%s' and `password`='%s'"%(u,p)
	print(q1)
	res=select(q1)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)

@api.route('/userregister')
def userregister():
	data={}
	f=request.args['fname']
	l=request.args['lname']
	
	pl=request.args['place']
	
	ph=request.args['phone']
	e=request.args['email']
	
	u=request.args['username']
	p=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(NULL,'%s','%s','student')"%(u,p)
		lid=insert(q)
		r="insert into student values(NULL,'%s','%s','%s','%s','%s','%s')"%(lid,f,l,pl,ph,e)
		insert(r)
		print(r)
		data['status']="success"
	return str(data)

@api.route('/viewcourse')
def viewcourse():
	data={}
	
	q="select * from course inner join managers using (manager_id)"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="viewcourse"
	return str(data)



@api.route('/viewrequest')
def viewrequest():
	data={}
	
	q="SELECT * FROM `request` INNER JOIN `course` USING (`course_id`) INNER JOIN `student` USING (`student_id`)"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="viewrequest"
	return str(data)


@api.route('/Makepayment')
def Makepayment():
	data={}
	lid=request.args['login_id']
	amt=request.args['amt']
	rid=request.args['rid']

	q="insert into payment values(null,'%s','%s',curdate())"%(rid,amt)
	insert(q)
	print(q)
	q="update request set status='Paid' where request_id='%s'"%(rid)
	update(q)
	data['status']='success'
	return str(data)

@api.route('/Viewsyllabus')
def Viewsyllabus():
	data={}
	
	q="SELECT * FROM `syllabus` INNER JOIN `course` USING (`course_id`)"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="Viewsyllabus"
	return str(data)

@api.route('/Viewtutordetails')
def Viewtutordetails():
	data={}
	
	q="SELECT * FROM `tutors` "
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="Viewtutordetails"
	return str(data)


@api.route('/send_complaint')	
def send_complaint():
	data={}
	lid=request.args['login_id']
	c=request.args['complaint']
	q="insert into `complaint` values(null,(select student_id from student where login_id='%s'),'%s','pending',curdate())"%(lid,c)
	insert(q)
	print(q)
	data['status']="success"
	data['method']="send_complaint"
	return str(data)

@api.route('/view_complaints')
def view_complaints():
	data={}
	lid=request.args['login_id']
	q="select * from complaint inner join student on student.student_id=complaint.sender_id where login_id='%s'"%(lid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="view_complaints"
	return str(data)


@api.route('/rate')
def rate():
	data={}
	rate=request.args['rating']
	log_id=request.args['log_id']
	review=request.args['review']
	
	q="insert into rating values(null,'%s','%s','%s',curdate())"%(log_id,rate,review)
	insert(q)
	print(q)
	data['status']="success"
	return str(data)


@api.route('/public_view_videos')
def public_view_videos():
	data={}
	cid=request.args['cid']
	
	q="select * from videos where course_id='%s'"%(cid)
	res=select(q)
	print(q)
	data['data']=res
	data['status']="success"
	data['method']="public_view_videos"
	return str(data)


@api.route('/viewusers')
def viewusers():
	data={}
	login_id=request.args['lid']
	q="select * from student where login_id='%s'"%(login_id)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
		data['method']='viewusers'
	return str(data)

@api.route('/updateuser')	
def updateuser():
	data={}
	name=request.args['name']
	place=request.args['place']
	phone=request.args['Phone']
	email=request.args['email']
	age=request.args['age']
	login_id=request.args['login_id']

	q="update `student` set firstname='%s',lastname='%s',phone='%s',email='%s',place='%s' where login_id='%s'"%(name,place,phone,email,age,login_id)
	update(q)
	print(q)
	data['status']='success'
	data['method']='updateuser'
	return str(data)

