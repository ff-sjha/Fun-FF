<?php
 
/**
 * @file
 * Definition of Drupal\site_manager\Plugin\views\field\CustomPointsCalField
 */
 
namespace Drupal\site_manager\Plugin\views\field;
 
use Drupal\Core\Form\FormStateInterface;
use Drupal\node\Entity\NodeType;
use Drupal\views\Plugin\views\field\FieldPluginBase;
use Drupal\views\ResultRow;
use Drupal\node\Entity\Node;
 
/**
 * Field handler to flag the node type.
 *
 * @ingroup views_field_handlers
 *
 * @ViewsField("custom_points_cal_field")
 */
class CustomPointsCalField extends FieldPluginBase {
 
  /**
   * @{inheritdoc}
   */
  public function query() {
    // Leave empty to avoid a query on this field.
  }
 
  
  /**
   * @{inheritdoc}
   */
  public function render(ResultRow $values) {
    $season_id = $values->_entity->field_fm_season->target_id;
    $player_id = $values->_relationship_entities['field_fm_franchise_players']->field_sp_player->target_id;
    $database = \Drupal::database();
    $points = array();
    $season_query = $database->select('node__field_mp_season', 'n');
    $season_query->addField('n', 'entity_id');
    $season_query->condition('n.field_mp_season_target_id', $season_id);
    $fm_season_ids = $season_query->execute()->fetchCol();
    if (!empty($fm_season_ids)) {
      $fm_query = $database->select('node__field_mp_franchise_players_poin', 'n1');
      $fm_query->addField('n1', 'field_mp_franchise_players_poin_target_id');
      $fm_query->condition('n1.entity_id', $fm_season_ids, 'IN');
      $fm_ids = $fm_query->execute()->fetchCol();

      if (!empty($fm_ids)) {
        $point_query = $database->select('paragraph__field_pp_player_points_earned', 'tp');
        $point_query->leftjoin('paragraph__field_pp_franchise_player', 'ts', 'ts.entity_id = tp.entity_id');
        
        $points = $point_query
          ->fields('tp', ['field_pp_player_points_earned_value'])
          ->condition('ts.field_pp_franchise_player_target_id', $player_id)
          ->condition('ts.entity_id', $fm_ids, 'IN')
          ->execute()
          ->fetchCol();
        }
      
      }
      if (!empty($points)) {
        return array_sum($points);
      } else {
        return 0;
      }
    
  }
}