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
class PlayerPointsForm extends FormBase {
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
  $tournament_id = 0;
  if ($form_state->getValue('tournament')) {		
    $tournament_id = $form_state->getValue('tournament');		
  }
  $all_users = user_load_multiple();
  $user_array = array();
  foreach($all_users as $user) {
    $user_array[$user->get('uid')->value] = $user->get('name')->value;
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
   ->fields('fm', ['entity_id'])
   ->fields('fm', ['field_fm_franchise_target_id'])
   ->condition('sn.field_fm_season_target_id', $season_id)
   ->execute()
   ->fetchAll();

  $players_array = array();
  foreach($franc_and_season as $frn_sn_obj) {
    $players = $database->select('paragraph__field_sp_player', 'spplyr');
    $players->leftjoin('node__field_fm_franchise_players', 'fmplyr', 'spplyr.entity_id = fmplyr.field_fm_franchise_players_target_id');
        
    $player_ids = $players
      ->fields('spplyr', ['field_sp_player_target_id'])
      ->condition('fmplyr.entity_id', $frn_sn_obj->entity_id)
      ->execute()
      ->fetchCol();
    if (!empty($player_ids)) {
      foreach($player_ids as $plyr_id) {
        $players_array[$plyr_id] = $franchise_array[$frn_sn_obj->field_fm_franchise_target_id];
      }
    }
  }   
	
  $mp_query = $database->select('node__field_match_tournament', 'mt');
  $mp_query->leftjoin('node__field_match_season', 'ms', 'ms.entity_id = mt.entity_id');
        
  $mp_query->fields('mt', ['entity_id']);
  if ($tournament_id != 0) {
    $mp_query->condition('mt.field_match_tournament_target_id', $tournament_id);
  }
  $mp_query->condition('ms.field_match_season_target_id', $season_id);
  $mp_players_id = $mp_query->execute()->fetchCol();
      
  if (!empty($mp_players_id)) {
    $fran_query = $database->select('node__field_mp_franchise_players_poin', 'fr');
    $fran_query->leftjoin('node__field_mp_match', 'mp', 'mp.entity_id = fr.entity_id');
        
    $target_ids = $fran_query
     ->fields('fr', ['field_mp_franchise_players_poin_target_id'])
     ->condition('mp.field_mp_match_target_id', $mp_players_id, 'IN')
     ->execute()
     ->fetchCol();

    if (!empty($target_ids)) {
      $players_query = $database->select('paragraph__field_pp_franchise_player', 'pp');
      $players_query->leftjoin('paragraph__field_pp_player_points_earned', 'pe', 'pe.entity_id = pp.entity_id');
        
      $player_points = $players_query
        ->fields('pp', ['field_pp_franchise_player_target_id'])
        ->fields('pe', ['field_pp_player_points_earned_value'])
        ->condition('pp.entity_id', $target_ids, 'IN')
        ->execute()
        ->fetchAll();	

      if (!empty(!empty($player_points))) {
      	foreach($player_points as $player_obj) {
          if (array_key_exists($player_obj->field_pp_franchise_player_target_id, $tournament_players)) {
            $tournament_players[$player_obj->field_pp_franchise_player_target_id] = $tournament_players[$player_obj->field_pp_franchise_player_target_id] + $player_obj->field_pp_player_points_earned_value;
          } else {
            $tournament_players[$player_obj->field_pp_franchise_player_target_id] = $player_obj->field_pp_player_points_earned_value;
          }
        }
      }
    }
  }
  $players_and_points = array();
  foreach($tournament_players as $p_id => $p_point) {
  	$players_and_points[] = array($season_array[$season_id],
    		                      $user_array[$p_id],
      		                      $players_array[$p_id],
      		                      $p_point);
  }

  $sorted_points = array();
  foreach($players_and_points as $point_array) {
    $sorted_points[] = $point_array[3];
  }
  array_multisort($sorted_points, SORT_DESC, $players_and_points);
	
	
  //  Build Form
	// $season_terms = \Drupal::entityManager()->getStorage('taxonomy_term')->loadTree("sports_season"); 
	// $season_type_terms = array();
	// foreach ($season_terms as $season_term) {
	// 	$season_type_terms[$season_term->tid] = $season_term->name;
	// }  
	$tournament_terms = \Drupal::entityManager()->getStorage('taxonomy_term')->loadTree("games_type"); 
	$tournament_type_terms = array();
	foreach ($tournament_terms as $tournament_term) {
		$tournament_type_terms[$tournament_term->tid] = $tournament_term->name;
	}  	
	
	$form['wrapper'] = [
	  '#type' => 'container',
	  '#attributes' => ['id' => 'totalpoints-filter-wrapper', 'class' => ['totalpoints-wrapper']],
	];
	
	$form['wrapper']['season'] = array(
	  '#title' => t('Season'),
	  '#type' => 'select',
	  '#options' => $season_array,
	  '#default_value' => 2,
	);	 

	$form['wrapper']['tournament'] = array(
	  '#title' => t('Tournament'),
	  '#type' => 'select',
	  '#options' => array(0 => '- Any -') + $tournament_type_terms,
	);	
	  
	$form['wrapper']['submit'] = array(
      '#type' => 'submit',
      '#value' => t('Apply'),
    );
	
	$header = array('Season', 'Player', 'Team' ,'Points');		
	$form['totalpoints_table'] = array(
	  '#type' => 'table',
	  '#header' => $header,
	  '#rows' => $players_and_points,
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