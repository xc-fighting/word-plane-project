<?php
function check_register($username,$password,$role)
{
	$conn=mysql_connect("localhost:3306","root","");
//	if($conn)
//	{
//		echo 'link success'.'<br/>';
//	}
//	else
//	{
//		die('link error'.mysql_error());
//	}
	mysql_select_db("plane_fight_3d",$conn);
	$sql_query="select username from user_table where username='".$username."'";
	$result=mysql_query($sql_query,$conn);// OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$sql_query);
	$num=mysql_num_rows($result);
	if($num!=0)
	{
//		$row=mysql_fetch_array($result);
//		echo '<pre>';
//		print_r($row);
		mysql_close($conn);
		return false;
	}
	else
	{
		$sql_insert="insert into user_table(username,password,role) values('".$username."','".$password."',".$role.")";
		$result1=mysql_query($sql_insert,$conn);// OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$sql_insert);
		mysql_close($conn);
		return true;
	}
}
error_reporting(0);
$username_reg=$_POST['user_name_reg'];
$password_reg=$_POST['password_reg'];
$role_reg=$_POST['role_reg'];
if(check_register($username_reg, $password_reg,$role_reg)==true)
{
	echo 'register success';
}
else 
{
	echo 'register fail';
}


