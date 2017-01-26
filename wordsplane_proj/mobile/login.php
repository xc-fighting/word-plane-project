<?php
function check_login($username,$password)
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
	$sql_query="select username from user_table where username='".$username."' and password='".$password."'";
	$result=mysql_query($sql_query,$conn) OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$sql_query);
	$num=mysql_num_rows($result);
	if($num!=0)
	{
		$row=mysql_fetch_array($result);
	//	echo '<pre>';
	//	print_r($row);
		mysql_close($conn);
		return true;
	}
	else 
	{
		mysql_close($conn);
		return false;
	}
	
	
}

error_reporting(0);
$user=$_POST['user_name_login'];
$pass=$_POST['password_login'];

if(check_login($user, $pass)==true)
{
   echo 'success';
}
else 
{
	echo 'fail';
}
