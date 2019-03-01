<?php
namespace Drupal\site_manager\Form;
use Drupal\Core\Form\FormBase;
use Drupal\Core\Form\FormStateInterface;
use Drupal\Core\Database\Database;
use Symfony\Component\HttpFoundation\RedirectResponse;

/**
 * Class MydataForm.
 *
 * @package Drupal\mydata\Form
 */
class MydataForm extends FormBase {
/**
 * {@inheritdoc}
 */
 public function getFormId() {
	return 'mydata_form';
 }

/**
 * {@inheritdoc}
 */
 public function buildForm(array $form, FormStateInterface $form_state) {

	 $form['nid'] = array(
	   '#type' => 'textfield',
	   '#title' => t('Nid'),
	   //'#default_value' => (isset($record['mobilenumber']) && $_GET['num']) ? $record['mobilenumber']:'',
	 );
	 
	/*
    if ($form_state->getValue('nid')) {
      // Show the value the user entered.
	  echo "Swati";
    }
	*/

	$form['submit'] = array(
      '#type' => 'submit',
      '#value' => t('Submit'),
    );
	
    if ($form_state->getValue('nid')) {
        $header = array(
          array('key' => t('Key')),
          array('value' => t('Value')),
        );
        $output = array();
        foreach ($form_state->getValues() as $key => $value) {
           $output['key'] = $key;
           $output['value'] = $value;
        }
		//echo "<pre>";
		//print_r($output);
		
        $header = array(
            array('data' => t('ID'), 'field' => 'st.nid'),
            array('data' => t('Type'), 'field' => 'st.type'),
            array('data' => t('Lang'), 'field' => 'st.langcode'),
        );
        $query = db_select('node', 'st')
        ->fields('st', array('nid', 'type', 'langcode'))
		->condition('st.type', 'tournaments')
		->condition('st.nid', $form_state->getValue('nid'))
		->orderBy('st.nid', 'ASC')
        ->extend('Drupal\Core\Database\Query\TableSortExtender')
        ->extend('Drupal\Core\Database\Query\PagerSelectExtender')->limit(20)
        ->orderByHeader($header);
        $data = $query->execute();
		
        $rows = array();
        foreach ($data as $row) {
          $rows[] = array('data' => (array) $row);
        }
		/*
        $build['table_pager'][] = array(
            '#type' => 'table',
            '#header' => $header,
            '#rows' => $rows,
        );
        $build['table_pager'][] = array(
            '#type' => 'pager',
        );	
        */

		
        $form['mytable'] = array(
          '#type' => 'table',
          '#header' => $header,
          '#rows' => $rows,
          '#empty' => t('No Data'),
		  '#attributes' => array(
			'class' => array('custom-table'),
		  ),		  
        );
    }		
	return $form;
 }
 
 

 /**
 * {@inheritdoc}
 */
 public function submitForm(array &$form, FormStateInterface $form_state) {

	$field=$form_state->getValues();
	$nodeid=$field['nid'];
 
    $form_state->setRebuild();
	/*
	//echo $nodeid;
	$build = array(
          '#markup' => $nodeid
        );
       return $build;
	   */
	
	
	
	//$response = new RedirectResponse("testpoints");
	//$response->send();
 }
}