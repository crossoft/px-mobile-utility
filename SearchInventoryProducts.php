<?php
include_once('Connection.php');
header('Content-type: application/json');
extract($_REQUEST);
$inputJSON = file_get_contents('php://input');
$input = json_decode( $inputJSON, TRUE ); 

$accountId =(!empty($input['accountId']))? $input['accountId'] : '';
$Name = (!empty($input['Name'])) ? $input['Name'] : '';
 
if(empty($input['Name']) OR empty($input['accountId']))
{
$message['status'] = '0';
$message['message'] = 'No record found';
send_response($message);
exit;
}else{
    
if(isset($Name) && $Name!='' AND (isset($accountId))){
   // $q['query'] = "SELECT * FROM mac WHERE (Company LIKE '%vendorName%')";
     $q['query']="SELECT * FROM  inventory_products WHERE accountId = '$accountId' AND (name like '%$Name%' OR vendorName like '%$Name%')  ORDER BY name"; 
    //var_dump($q['query']); die;
}
  $q['run'] =$conn->query($q['query']);
    // var_dump(mysqli_error($conn));
  
    $all_searched_user= array();
    if($q['run']->num_rows != '0')
      {
    
      while ($q['result'] = $q['run']->fetch_assoc())
     {  
       $user = $q['result'];
       array_push($all_searched_user, $user);
    }
   }
       if (COUNT($all_searched_user) > 0)
     {
        $message['status'] = 1;
        $message['message']='search found';
        $message['data'] = $all_searched_user;
       send_response($message);    
       
   }
else
{
    $message["status"] = 0;
    $message["message"] = 'Your search did not return any results';
    send_response($message);
}
}
function send_response($message)
{
	echo json_encode($message);
} 
?>
