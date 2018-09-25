<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 
$accountId=$input['accountId'];
$vendorName=$input['vendorName'];
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

 
function get_all_item($accountId,$vendorName ) 
{
    global $conn;
    $q['query'] ="SELECT inventoryProductId,name,minLevel,maxLevel,reorderState,receivingInstructions,ingredientInstructions,vendorSku,maxDaysBetweenLogging,logDueDateOverride,primaryZone,primaryBin,secondaryZone,secondaryBin,calculatedQty,maxLevel-calculatedQty AS reorderQty,vendorName FROM `inventory_products` WHERE accountId='$accountId' AND vendorName='$vendorName' AND reorderState='Reorder'";
  //$q['query'] ="SELECT * FROM `inventory_products` WHERE accountId='$accountId' AND vendorName='$vendorName' ORDER BY name DESC";
    //var_dump($q['query']);die;
    $q['run'] = $conn->query($q['query']);
    $all_details=array();
    if($q['run']->num_rows != '0')
    { 
        //var_dump($q['run']);
        while ($q['result'] = $q['run']->fetch_assoc())
        {
           
                 array_push($all_details, $q['result']);
                
 
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
    $message = array('status'=>'0','message'=>'record not found');
    send_response($message);
}
?>

