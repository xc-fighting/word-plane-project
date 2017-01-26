<?php
function upload()
{
    
	
	$tmp_path=$_FILES['files']['tmp_name'];
	$fp=fopen($tmp_path,'r')or die("Unable to open file!");
	$line=null;
	while(!feof($fp))
	{
		$line.=fgetc($fp);
	}
	if($line!=null)
	{
		$arr=explode('-', $line);
		$index=count($arr)-1;
		$username=explode('=',$arr[$index])[1];
		$target_path  = "shengci_files/";//接收文件目录
		$target_path = $target_path .$username. basename( $_FILES['files']['name']);
		if(move_uploaded_file($_FILES['files']['tmp_name'], $target_path)) 
		{
			//echo "The file ".  basename( $_FILES['files']['name']). " has been uploaded";
			$conn=mysql_connect('localhost:3306','root','');
			mysql_select_db('plane_fight_3d',$conn);
			$sql="select * from shengciben where username='".$username."'";
			$result=mysql_query($sql,$conn);
			if(mysql_num_rows($result)==0)
			{
				$insert="insert into shengciben(username,addr) values('".$username."','".$target_path."')";
				mysql_query($insert);
			}
			return true;
			
		}  
		else
		{
			//echo "There was an error uploading the file, please try again!" . $_FILES['files']['error'];
			return false;
		}
		
		
	}
	
	
	
}
error_reporting(0);
$flag=upload();
if($flag==true)echo 'success';
else echo 'fail';
