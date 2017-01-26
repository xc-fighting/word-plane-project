<?php
 function get_liuyan($user)
 {
 	$result_str=null;
 	$conn=mysql_connect("localhost:3306","root","");
 	mysql_select_db("plane_fight_3d",$conn);
 	$sql="select content,time from lianxi_table where username"."='".$user."'";
 	$result=mysql_query($sql,$conn);//OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$sql);
 	$num=mysql_num_rows($result);
 	if($num==0)
 	{
 		return $result_str;
 	}
 	else 
 	{
 		while(($arr=mysql_fetch_array($result))!=null)
 		{
 			$result_str.=$arr['content']."-".$arr['time'];
 		}
 		return $result_str;
 	}
 }
 error_reporting(0);
 $name=$_POST['getliuyan_name'];
 $str=get_liuyan($name);
 if($str==null)echo 'get fail';
 else
 echo $str;
 