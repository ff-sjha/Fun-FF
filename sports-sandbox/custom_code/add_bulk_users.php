<?php

/**
 * @file
 * The PHP page that serves all page requests on a Drupal installation.
 *
 * All Drupal code is released under the GNU General Public License.
 * See COPYRIGHT.txt and LICENSE.txt files in the "core" directory.
 */

use Drupal\Core\DrupalKernel;
use Symfony\Component\HttpFoundation\Request;

$autoloader = require_once 'autoload.php';

$kernel = new DrupalKernel('prod', $autoloader);

$request = Request::createFromGlobals();
$response = $kernel->handle($request);
/*
$response->send();
$kernel->terminate($request, $response);
*/


use Drupal\user\Entity\User;

create_new_user('Amol', '123456', 'Amol', 'Swami', 'Amol.Swami@firstfuel.com');
create_new_user('Anand', '123456', 'Anand', 'Mattikopp', 'Anand.Mattikopp@firstfuel.com');
create_new_user('Anil', '123456', 'Anil', 'Galve', 'Anil.Galve@firstfuel.com');
create_new_user('Ankit', '123456', 'Ankit', 'Agrawal', 'Ankit.Agrawal@firstfuel.com');
create_new_user('Archana', '123456', 'Archana', 'Jain', 'Archana.Jain@firstfuel.com');
create_new_user('Ashwin', '123456', 'Ashwin', 'Markande', 'Ashwin.Markande@firstfuel.com');
create_new_user('Ashwini', '123456', 'Ashwini', 'Pardeshi', 'Ashwini.Pardeshi@firstfuel.com');
create_new_user('Avinash', '123456', 'Avinash', 'Kelkar', 'Avinash.Kelkar@firstfuel.com');
create_new_user('Bapu', '123456', 'Bapu', 'Labade', 'Bapu.Labade@firstfuel.com');
create_new_user('Chandrashekhar', '123456', 'Chandrashekhar', 'Bora', 'Chandrashekhar.Bora@firstfuel.com');
create_new_user('Chirag', '123456', 'Chirag', 'Viradiya', 'Chirag.Viradiya@firstfuel.com');
create_new_user('Daksha', '123456', 'Daksha', 'Sadolikar', 'Daksha.Sadolikar@firstfuel.com');
create_new_user('Eggima', '123456', 'Eggima', 'Pereira', 'Eggima.Pereira@firstfuel.com');
create_new_user('Gaurava', '123456', 'Gaurav', 'Ashwin', 'Gaurav.Ashwin@firstfuel.com');
create_new_user('Gauravd', '123456', 'Gaurav', 'Deshpande', 'Gaurav.Deshpande@firstfuel.com');
create_new_user('Kishor', '123456', 'Kishor', 'Khadtare', 'Kishor.Khadtare@firstfuel.com');
create_new_user('Mahesh', '123456', 'Mahesh', 'Kulkarni', 'Mahesh.Kulkarni@firstfuel.com');
create_new_user('Medha', '123456', 'Medha', 'Wagh', 'Medha.Wagh@firstfuel.com');
create_new_user('Neerav', '123456', 'Neerav', 'Kulshreshtha', 'Neerav.Kulshreshtha@firstfuel.com');
create_new_user('Paramita', '123456', 'Paramita', 'Sen', 'Paramita.Sen@firstfuel.com');
create_new_user('Paras', '123456', 'Paras', 'Patel', 'Paras.Patel@firstfuel.com');
create_new_user('Payel', '123456', 'Payel', 'Paul', 'Payel.Paul@firstfuel.com');
create_new_user('Prakash', '123456', 'Prakash', 'Jha', 'Prakash.Jha@firstfuel.com');
create_new_user('Priya', '123456', 'Priya', 'Gupta', 'Priya.Gupta@firstfuel.com');
create_new_user('Pushpendra', '123456', 'Pushpendra', 'Kumar', 'Pushpendra.Kumar@firstfuel.com');
create_new_user('Rishabh', '123456', 'Rishabh', 'Sood', 'Rishabh.Sood@firstfuel.com');
create_new_user('Rohanc', '123456', 'Rohan', 'Chavan', 'Rohan.Chavan@firstfuel.com');
create_new_user('Rohand', '123456', 'Rohan', 'Datar', 'Rohan.Datar@firstfuel.com');
create_new_user('Sachin', '123456', 'Sachin', 'Khatavkar', 'Sachin.Khatavkar@firstfuel.com');
create_new_user('Sameer', '123456', 'Sameer', 'Khare', 'Sameer.Khare@firstfuel.com');
create_new_user('Sneha', '123456', 'Sneha', 'Kalore', 'Sneha.Kalore@firstfuel.com');
create_new_user('Sourabh', '123456', 'Sourabh', 'Gite', 'Sourabh.Gite@firstfuel.com');
create_new_user('Sujay', '123456', 'Sujay', 'Bawaskar', 'Sujay.Bawaskar@firstfuel.com');
create_new_user('Sumit', '123456', 'Sumit', 'Desai', 'Sumit.Desai@firstfuel.com');
create_new_user('Surbhi', '123456', 'Surbhi', 'Gupta', 'Surbhi.Gupta@firstfuel.com');
create_new_user('Sushil', '123456', 'Sushil', 'Damdhere', 'Sushil.Damdhere@firstfuel.com');
create_new_user('Swati', '123456', 'Swati', 'Jha', 'Swati.Jha@firstfuel.com');
create_new_user('Tushar', '123456', 'Tushar', 'Gandhi', 'Tushar.Gandhi@firstfuel.com');
create_new_user('Arshi', '123456', 'Arshi', 'Khan', 'Arshi.Khan@firstfuel.com');
create_new_user('Mangesh', '123456', 'Mangesh', 'Malwade', 'mangesh.malwade@firstfuel.com');
create_new_user('Riddhi', '123456', 'Riddhi', 'Potdar', 'riddhi.potdar@firstfuel.com');
echo "done";
exit();

/**
* Helper function to create a new user.
*/
function create_new_user($username, $password, $fname, $lname, $email){
  $user = User::create();

  //Mandatory settings
  $user->setPassword($password);
  $user->enforceIsNew();
  $user->setEmail($email);

  //This username must be unique and accept only a-Z,0-9, - _ @ .
  $user->setUsername($username);
  $user->addRole('player');
  
  $user->set("field_user_firstname", $fname);
  $user->set("field_user_lastname", $lname);

  //Optional settings
  /*
  $language = 'en';
  $user->set("init", 'email');
  $user->set("langcode", $language);
  $user->set("preferred_langcode", $language);
  $user->set("preferred_admin_langcode", $language);
  */
  $user->activate();

  //Save user
  $user->save();
  //drupal_set_message("User with uid " . $user->id() . " saved!\n");
}



