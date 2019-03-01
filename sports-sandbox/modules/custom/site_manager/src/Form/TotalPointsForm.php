<?php
namespace Drupal\site_manager\Form;
use Drupal\Core\Form\FormBase;
use Drupal\Core\Form\FormStateInterface;
use Drupal\Core\Database\Database;
use Drupal\Core\Entity\EntityInterface;
use Symfony\Component\HttpFoundation\RedirectResponse;

/**
 * Class MydataForm.
 *
 * @package Drupal\mydata\Form
 */
class TotalPointsForm extends FormBase {
/**
 * {@inheritdoc}
 */
 public function getFormId() {
	return 'totalpoints_form';
 }

/**
 * {@inheritdoc}
 */
 public function buildForm(array $form, FormStateInterface $form_state) {

	// Build Table - Starts
	$season_id = 2;
    if ($form_state->getValue('season')) {		
	  $season_id = $form_state->getValue('season');		
    }
	
	$franchise = \Drupal::entityManager()->getStorage('taxonomy_term')->loadTree("franchise");
	$franchise_array = array();
	foreach ($franchise as $franch_term) {
	  $franchise_array[$franch_term->tid] = $franch_term->name;
	}

	$season_terms = \Drupal::entityManager()->getStorage('taxonomy_term')->loadTree("sports_season");
	$season_array = array();
	foreach ($season_terms as $sesn_term) {
	  $season_array[$sesn_term->tid] = $sesn_term->name;
	}

	$database = \Drupal::database();
	$query = $database->select('node__field_fm_franchise', 'fm');
	   $query->leftjoin('node__field_fm_season', 'sn', 'sn.entity_id = fm.entity_id');

	   $franc_and_season = $query
	   ->fields('fm', ['field_fm_franchise_target_id'])
	   ->fields('sn', ['field_fm_season_target_id'])
	   ->condition('sn.field_fm_season_target_id', $season_id)
	   ->execute()
	   ->fetchAll();
	   
	   
	$total_points_array = array();
	foreach ($franc_and_season as $fmsn) {
	  $total_points_array[] = array($season_array[$fmsn->field_fm_season_target_id],
									$franchise_array[$fmsn->field_fm_franchise_target_id]);		
	}
	// Build Table - Ends
	
	/*
	echo "<pre>";
	print_r($total_points_array);
	die;
	);*/
	
	// Build Form
	$season_terms = \Drupal::entityManager()->getStorage('taxonomy_term')->loadTree("sports_season"); 
	$season_type_terms = array();
	foreach ($season_terms as $season_term) {
		$season_type_terms[$season_term->tid] = $season_term->name;
	}  
	$form['season'] = array(
	  '#title' => t('Season'),
	  '#type' => 'select',
	  '#options' => $season_type_terms,
	  '#default_value' => 2,
	);	  
	  
	$form['submit'] = array(
      '#type' => 'submit',
      '#value' => t('Submit'),
    );
	
	$header = array('Season', 'A2');		
	$form['totalpoints_table'] = array(
	  '#type' => 'table',
	  '#header' => $header,
	  '#rows' => $total_points_array,
	  '#empty' => t('No Data'),
	  '#attributes' => array(
		'class' => array('custom-table'),
	  ),		  
	);
		
	return $form;
 }
 
 /**
 * {@inheritdoc}
 */
 public function submitForm(array &$form, FormStateInterface $form_state) {
    $form_state->setRebuild();
 }
}