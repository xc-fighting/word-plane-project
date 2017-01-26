<?php
function get_grades($username)
{
	
	$result_str=null;
	$conn=mysql_connect("localhost:3306","root","");
	mysql_select_db("plane_fight_3d",$conn);
	$sql="select * from grade_table order by grade desc";
	$result=mysql_query($sql,$conn)OR die("<br/>Error:<b>".mysql_error()."</b><br/>产生问题的sql".$sql);
	$num=mysql_num_rows($result);
	if($num==0)return null;
	else 
	{
		
		while(($arr=mysql_fetch_array($result))!=null)
		{
			
			$result_str.=$arr['username']."~".$arr['grade']."~".$arr['time']."~".$arr['wordnum']."|";
			
		}
		
		return $result_str;
	}
	
}
error_reporting(0);
$name=$_POST['get_grade_name'];
echo get_grades($name);