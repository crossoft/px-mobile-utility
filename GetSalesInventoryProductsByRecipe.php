<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 
//$accountId=$input['accountId'];

/*if($accountId==null)
{
    $message = array('status'=>'0','message'=>'account id required');
    send_response($message);
    Exit;
}*/
function send_response($message)
{
	echo json_encode($message);
} 

function get_all_item() 
{
    global $conn;
    $q['query'] ="SELECT inventoryProductName,qty,unitName,instructions,isCommon FROM `sales_inventory_products` ORDER BY  CASE sizeName WHEN 'Baby' THEN 0 WHEN 'Small/Default' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Large' THEN 3 END, CASE isCommon WHEN 1 THEN 0 WHEN 0 THEN 1 END,  ordinal";
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
$data = get_all_item();
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

