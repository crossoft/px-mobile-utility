<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 
 //$userId  = $input['userId'];
 $userName  = $input['userName'];
 $userPass  =$input['userPass'];



//send_response create
function send_response($message)
{
	echo json_encode($message);
}
if($userName==null)
{
    $message = array("Status"=>0,"Message"=>"Parameter missing ",);
       send_response($message);
       exit;
}
// function call
$all_found_data = check_user($userName, $userPass);

 //create function
function check_user($userName, $userPass) 
{
    global $conn;

$q['query']="SELECT userId,userName,userPass FROM logins WHERE (userName='$userName') AND userPass='$userPass'";
$q['run']=$conn->query($q['query']);

$q['result']=$q['run']->fetch_assoc();
    if ($q['result']) // if  result ka liye call
    { 
        $data = get_all_item($q['result']['userId']);
       $message = array("Status"=>1,"Message"=>"logged successfully",'id'=>$q['result']['userId'],'data'=>$data);
       send_response($message);
       exit;
   }
   else
   {
       $message = array("Status"=>0,"Message"=>"Wrong credentials");
       send_response($message);
       exit;
   }
}

function get_all_item($userId) 
{
    global $conn;
    $q['query'] = "SELECT * FROM logins WHERE userId=$userId";
    $q['run'] = $conn->query($q['query']);
    $all_supplier =array();
    if($q['run']->num_rows != '0')
    {
           $q['result'] = $q['run']->fetch_assoc();
            $q['result']['userID']=(int)$q['result']['userId']; 
            return $q['result'];
            
    }
   
}
    return $all_supplier;
   

?>