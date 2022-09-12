<?php
$conn=mysqli_connect("localhost","root","");
mysqli_selecdb($conn,"apidb");

	$name=trim($_POST['name']);
	$email=trim($_POST['email']);
	$pswd=trim($_POST['password']);

	$qry1="select * from tbl_user where email='$email'";
	$raw=,ysqli_query($conn,$qry1);
	$count=mysqli_num_rows($raw);

	if($count>)
	{
		$response="exit";
	}
	else
	{
		$qry2="INSERT INTO 'tbl_user' ('id', 'name', 'email', 'password') VALUES (NULL,'$name', '$email', '$pswd')";

	$res=mysqli_query($conn,$qry2);
	if($res==true)
	$response="inserted";
	else
	$response="failed";
	}
	echo $response;

?>