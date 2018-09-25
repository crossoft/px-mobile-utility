<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 
$accountId=$input['accountId'];
if($accountId==null)
{
    $message = array('status'=>'0','message'=>'account id required');
    send_response($message);
    Exit;
}
function send_response($message)
{
	echo json_encode($message);
} 

function get_all_item($accountId) 
{
    global $conn;
    $q['query'] ="SELECT * FROM `time_keeping` WHERE accountId='$accountId'";
    $q['run'] = $conn->query($q['query']);
    $all_details=array();
    if($q['run']->num_rows != '0')
    { 
        //var_dump($q['run']);
        while ($q['result'] = $q['run']->fetch_assoc())
        {
        

            array_push($all_details, $q['result']);
            //print_r($all_supplier);
            
        }
    }
    return $all_details;
   
}
//$data = get_all_supplier();
$data = get_all_item($accountId);
if($data)
{
$message = array('status'=>'1','message'=>'All Details successfully','data'=>$data);
send_response($message);
}else
{
    $message = array('status'=>'0','message'=>'record not found');
    send_response($message);
}
?>

