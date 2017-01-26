<?php
function check_insert($username,$grade,$wordnum,$time)
{
	$conn=mysql_connect("localhost:3306","root","");
	mysql_select_db("plane_fight_3d",$conn);
	$sql="select * from grade_table where username='".$username."'";
	$result=mysql_query($sql,$conn)OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$sql);
	$num=mysql_num_rows($result);
	if($num==0)
	{
		$insert="insert into grade_table(username,grade,wordnum,time) values('".$username."','".$grade."',".$wordnum.",'".$time."')";
		$result1=mysql_query($insert,$conn)OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$insert);
	    if($result!=null)return true;
	    else return false;
	}
	else 
	{
		$update="update grade_table set grade=".$grade.",time='".$time.", wordnum=".$wordnum." where username='".$username."'";
		$result2=mysql_query($update,$conn)OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$update);
		if($result2!=null)return true;
		else return false;
	}
}

error_reporting(0);
$username=$_POST['username_grade'];
$grade=$_POST['grade'];
$time=$_POST['time_grade'];
$wordnum=$_POST['wordnum'];
$flag=check_insert($username, $grade,$wordnum, $time);
if($flag==true)echo 'success!';
else echo 'fail!';

