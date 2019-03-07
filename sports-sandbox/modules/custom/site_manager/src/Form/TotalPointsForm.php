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
	  $total_points = '--';
	  //Get all franchise points target ids from tournament for selected season.
      $tp_query = $database->select('node__field_tour_franchise_points', 'fp');
      $tp_query->leftjoin('node__field_tour_season', 'tour', 'tour.entity_id = fp.entity_id');
      $team_points_ids = $tp_query
      ->fields('fp', ['field_tour_franchise_points_target_id'])
      ->condition('tour.field_tour_season_target_id', $fmsn->field_fm_season_target_id)
      ->execute()
      ->fetchCol();
    
      // Get all points for particular franchise from season specific target ids.
      if (!empty($team_points_ids)) {
        $query = $database->select('paragraph__field_tap_points', 'tp');
        $query->leftjoin('paragraph__field_tap_team', 'ts', 'ts.entity_id = tp.entity_id');
    
        $points = $query
        ->fields('tp', ['field_tap_points_value'])
        ->condition('ts.field_tap_team_target_id', $fmsn->field_fm_franchise_target_id)
        ->condition('ts.entity_id', $team_points_ids, 'IN')
        ->execute()
        ->fetchCol();
        if (!empty($points)) {
          $total_points = array_sum($points);
        }  
      }  	
	  $total_points_array[] = array($season_array[$fmsn->field_fm_season_target_id],
									$franchise_array[$fmsn->field_fm_franchise_target_id],
									$total_points);		
	}
	$sorted_points = array();
    foreach($total_points_array as $point_array) {
      $sorted_points[] = $point_array[2];
    }
    array_multisort($sorted_points, SORT_DESC, $total_points_array);
	// Build Table - Ends
	
	
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
	
	$header = array('Season', 'Franchise', 'Points');		
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