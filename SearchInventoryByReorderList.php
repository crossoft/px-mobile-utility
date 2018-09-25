<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 
$accountId=$input['accountId'];
$vendorName =$input['vendorName'];
if($accountId==null)
{
    $message = array('status'=>'0','message'=>'account id required');
    send_response($message);
    Exit;
}
if($vendorName==null)
{
    $message = array('status'=>'0','message'=>'Field cannot be blank');
    send_response($message);
    Exit;
}
function send_response($message)
{
	echo json_encode($message);
} 

function get_all_item($accountId,$vendorName ) 
{
    global $conn;
    $q['query'] ="SELECT inventoryProductId,name,minLevel,maxLevel,calculatedQty, maxLevel-calculatedQty AS reorderQty,vendorName FROM `inventory_products` WHERE accountId='$accountId' AND vendorName like '%$vendorName%' GROUP BY(vendorName) ";
    //var_dump($q['query']);die;
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
$data = get_all_item($accountId,$vendorName);
if($data)
{
$message = array('status'=>'1','message'=>'All Details successfully','data'=>$data);
send_response($message);
}else
{
    $message = array('status'=>'0','message'=>'Your search did not return any results');
    send_response($message);
}
?>

