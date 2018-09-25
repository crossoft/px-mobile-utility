<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 
$salesProductName=$input['salesProductName'];
//$accountId=$input['accountId'];

if($salesProductName==null)
{
    $message = array('status'=>'0','message'=>'Field cannot be blank');
    send_response($message);
    Exit;
}
function send_response($message)
{
	echo json_encode($message);
} 

function get_all_item($salesProductName) 
{
    global $conn;
    $q['query'] ="SELECT salesProductId AS product_id, salesProductName,inventoryProductName,qty,sizeName,unitName,instructions,ordinal,isCommon FROM `sales_inventory_products` WHERE salesProductName like '%$salesProductName%' GROUP BY (salesProductName)";
  //$q['query'] ="SELECT sizeName, inventoryProductName, qty, unitName, instructions FROM sales_inventory_products WHERE salesProductName like '%$salesProductName%' ORDER BY CASE sizeName WHEN 'Baby' THEN 0 WHEN 'Small/Default' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Large' THEN 3 END,CASE isCommon WHEN 1 THEN 0 WHEN 0 THEN 1 END,ordinal";


   // var_dump($q['query'] );die;
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
$data = get_all_item($salesProductName);
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

