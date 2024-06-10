/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - tutor_point
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tutor_point` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `tutor_point`;

/*Table structure for table `assigntocourse` */

DROP TABLE IF EXISTS `assigntocourse`;

CREATE TABLE `assigntocourse` (
  `assigncourse_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `tutor_id` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`assigncourse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `assigntocourse` */

insert  into `assigntocourse`(`assigncourse_id`,`course_id`,`tutor_id`,`date`) values 
(4,2,2,'2022-01-18'),
(5,1,1,'2022-01-22');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `complaint` varchar(500) DEFAULT NULL,
  `reply` varchar(500) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`sender_id`,`complaint`,`reply`,`date`) values 
(1,3,'sadvfbn cv ','wsdfghjk','2022-01-20'),
(2,6,'asdvgnm','Hellooo','2022-01-21'),
(3,6,'zxvcbvnb,,n bvcx','asdffjk.lmnvbvc','2022-01-21'),
(4,6,'sadfgbn','ZazxXC VBNM','2022-01-21'),
(5,6,'sdfgn','oh','2022-01-22'),
(6,3,'go km ','pending','2023-03-22'),
(7,3,'h','pending','2023-03-23'),
(8,4,'bdhdhd','pending','2023-04-01'),
(9,3,'ok','Pending','2023-04-01');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_id` int(11) DEFAULT NULL,
  `course` varchar(50) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  `duration` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `course` */

insert  into `course`(`course_id`,`manager_id`,`course`,`rate`,`duration`) values 
(1,1,' swd ','5000','30'),
(2,1,'sample','15000','120');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'mm','mm','Manager'),
(2,'admin','admin','admin'),
(3,'tt','tt','Tutor'),
(4,'qq','qq','Tutor'),
(5,'pp','pp','Tutor'),
(6,'ss','ss','Student'),
(8,'uu','uu','Student'),
(9,'h','h','student'),
(10,'hai','hai','student'),
(11,'wait','123','Pending');

/*Table structure for table `managers` */

DROP TABLE IF EXISTS `managers`;

CREATE TABLE `managers` (
  `manager_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `files` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`manager_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `managers` */

insert  into `managers`(`manager_id`,`login_id`,`firstname`,`lastname`,`place`,`phone`,`email`,`files`) values 
(1,1,'sam','john','ekm','7458961023','sam@gmail.com',NULL),
(2,11,'user','qwerty','kerala','9874634624','student@gmail.com','static/uploads6f4ccaa8-8641-479e-96e3-d74ba29ce81010855406.jpg');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `request_id` int(11) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`request_id`,`amount`,`date`) values 
(1,1,'5000','2022-01-21'),
(2,2,'15000','2023-03-22');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `rated` varchar(50) DEFAULT NULL,
  `details` varchar(500) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `rating` */

insert  into `rating`(`rating_id`,`student_id`,`rated`,`details`,`date`) values 
(1,1,'4','ghjk,ghcbvx','2022-01-22'),
(2,2,'3','sdfgh','2022-01-22'),
(3,9,'3.0','gsgsgs','2023-03-22'),
(4,10,'3.0','hdhhd','2023-04-01');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `request` */

insert  into `request`(`request_id`,`course_id`,`student_id`,`date`,`status`) values 
(1,1,1,'2022-01-21','Paid'),
(2,2,1,'2022-01-22','Paid');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `student` */

insert  into `student`(`student_id`,`login_id`,`firstname`,`lastname`,`place`,`phone`,`email`) values 
(1,6,'ss','ss','scdv','1234567890','ss@gmail.com'),
(2,8,'jhn','bn','bn','741852963','sdf@asdf.dfg'),
(3,9,'c','h','p','1234567891','y@gmail.com'),
(4,10,'vhhjs','czzbhs','vhuus','1234567123','guhhh@gmail.com');

/*Table structure for table `syllabus` */

DROP TABLE IF EXISTS `syllabus`;

CREATE TABLE `syllabus` (
  `syllabus_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `syllabus` varchar(5000) DEFAULT NULL,
  `timetaken` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`syllabus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

/*Data for the table `syllabus` */

insert  into `syllabus`(`syllabus_id`,`course_id`,`syllabus`,`timetaken`) values 
(2,1,' aDSFG','10'),
(3,1,' RDFGHJK','15'),
(7,1,' wesrdfgvhbjk','5'),
(8,2,'sasddfdgfhggcvc',' 1 '),
(9,2,' asd','1'),
(10,2,' qwsd','1'),
(11,2,' asdf','6'),
(12,2,' wqerty','90');

/*Table structure for table `tutors` */

DROP TABLE IF EXISTS `tutors`;

CREATE TABLE `tutors` (
  `tutor_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tutor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tutors` */

insert  into `tutors`(`tutor_id`,`login_id`,`manager_id`,`firstname`,`lastname`,`place`,`phone`,`email`) values 
(1,3,1,'tt','ooooo','tcr','9632587410','tt@gmail.com'),
(2,4,1,'qq','ww','eeer','8523697410','qq@gmail.com'),
(3,5,1,'pp','pp','pp','7458963210','pp@gmail.com');

/*Table structure for table `videos` */

DROP TABLE IF EXISTS `videos`;

CREATE TABLE `videos` (
  `video_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `videodetails` varchar(500) DEFAULT NULL,
  `videos` varchar(500) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `videos` */

insert  into `videos`(`video_id`,`course_id`,`videodetails`,`videos`,`date`) values 
(1,1,'gb n','static/uploads/videos/a3e09c79-9824-4f85-a776-0d9cbdf789a4a2.jpeg','2022-01-21'),
(2,1,'ASXDCXV','static/uploads/videos/4551e8aa-c528-4f38-87d1-576a855915f9aa1.jpg','2022-01-21'),
(3,1,'xzcvb','static/uploads/videos/f2093faa-9149-4cfc-8f96-3427af136a33mov_bbb.mp4','2022-01-21'),
(4,1,'asfdgdgfhjhk','static/uploads/videos/f8dcf8a0-416c-493e-8761-e1d25ea61a49mov_bbb.mp4','2022-01-22');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
