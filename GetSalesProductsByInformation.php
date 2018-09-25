<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 
$accountId=$input['accountId'];
//$salesProductId=$input['salesProductId'];
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
$sql="SELECT description FROM sales_products WHERE accountId ='$accountId'";
    $result=mysqli_query($conn,$sql);
    $user=mysqli_fetch_assoc($result);
   $description = $user['description'];
function get_all_item($accountId,$description) 
{
    global $conn;
    $q['query'] ="SELECT salesProductName, sizeName, price FROM sales_product_prices WHERE accountId ='$accountId' ORDER BY CASE sizeName WHEN 'Baby' THEN 0 WHEN 'Small/Default' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Large' THEN 3 END";
    $q['run'] = $conn->query($q['query']);
    $all_details=array(); 
    if($q['run']->num_rows != '0')
    { 
        //var_dump($q['run']);
        while ($q['result'] = $q['run']->fetch_assoc())
        {
        $all_supplier =	array(
                'sizeName'                =>$q['result']['sizeName'],
                'price'=>$q['result']['price'],
                'description'=>$description,
            );
            
            array_push ($all_details,  $all_supplier );
        }
    }
    return $all_details;
   
}
//$data = get_all_supplier();
$data = get_all_item($accountId,$description);
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

