<?php
function connectus($username,$content,$time)
{
	$conn=mysql_connect("localhost:3306","root","");
	mysql_selectdb("plane_fight_3d", $conn);
	$sql_query1="select * from lianxi_table where username='".$username."'";
	
	$result=mysql_query($sql_query1,$conn);
	$num=mysql_num_rows($result);
	if($num==0)
	{
		$sql_insert="insert into lianxi_table(username,content,time) values "."('".$username."','".$content."','".$time."')";
		
		$result1=mysql_query($sql_insert,$conn);
		if($result1!=null) return true;
		else return false;
	}
	else 
	{
	  $sql_update="update lianxi_table set "."content="."'".$content."',"."time="."'".$time."' "."where username='".$username."'";
	 
	  $result2=mysql_query($sql_update,$conn);
	  if(result2!=null)return true;
	  else return false;	
	}
	
}
error_reporting(0);
$user=$_POST['user_lianxi'];
$content=$_POST['content_lianxi'];
$time=$_POST['time_lianxi'];
$flag=connectus($user, $content, $time);
if($flag==true)
	echo 'success';
else echo 'fail';



