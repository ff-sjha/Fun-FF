<?php
 
/**
 * @file
 * Definition of Drupal\site_manager\Plugin\views\field\CustomCalculatedField
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
 * @ViewsField("custom_calculated_field")
 */
class CustomCalculatedField extends FieldPluginBase {
 
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
    $total_points = '--';
    $nid = $values->nid;
    $node = Node::load($nid);
    $franchise_id = $node->field_fm_franchise->target_id;
    $season_id = $node->field_fm_season->target_id;
    $database = \Drupal::database();

    //Get all franchise points target ids from tournament for selected season.
    $tp_query = $database->select('node__field_tour_franchise_points', 'fp');
    $tp_query->leftjoin('node__field_tour_season', 'tour', 'tour.entity_id = fp.entity_id');
    $team_points_ids = $tp_query
      ->fields('fp', ['field_tour_franchise_points_target_id'])
      ->condition('tour.field_tour_season_target_id', $season_id)
      ->execute()
      ->fetchCol();
    
    // Get all points for particular franchise from season specific target ids.
    if (!empty($team_points_ids)) {
      $query = $database->select('paragraph__field_tap_points', 'tp');
      $query->leftjoin('paragraph__field_tap_team', 'ts', 'ts.entity_id = tp.entity_id');
    
      $points = $query
        ->fields('tp', ['field_tap_points_value'])
        ->condition('ts.field_tap_team_target_id', $franchise_id)
        ->condition('ts.entity_id', $team_points_ids, 'IN')
        ->execute()
        ->fetchCol();
      if (!empty($points)) {
        $total_points = array_sum($points);
      }  
    }  
    return $total_points;
  }
}