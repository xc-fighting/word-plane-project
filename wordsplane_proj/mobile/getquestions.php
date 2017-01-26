<?php
function get_ques_string($username)
{
$conn=mysql_connect('localhost:3306','root','');
mysql_select_db('plane_fight_3d',$conn);
$sql="select * from user_table,user_question,question_table where user_table.userId=user_question.userId and question_table.questionId
		=user_question.questionId and username='".$username."'";
$result=mysql_query($sql,$conn);
$num=mysql_num_rows($result);
if($num==0)return null;
else
{
	$str=null;
	while(($arr=mysql_fetch_array($result))!=null)
		{
				$str.=$arr['content'].'~'.$arr['question1'].'~'.
						$arr['question2'].'~'.$arr['question3'].'~'.$arr['question4'].
						'~'.$arr['answer'].'-';
		}
		return $str;
}
}
error_reporting(0);

$name=$_POST['stu_name'];
$str= get_ques_string($name);
if($str==null)echo 'fail';
else 
echo $str;
