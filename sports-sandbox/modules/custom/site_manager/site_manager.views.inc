<?php
/**
 * Implements hook_views_data_alter().
 */
function site_manager_views_data_alter(&$data) {
/*
  $data['node']['node_type_flagger'] = array(
    'title' => t('Node type flagger'),
    'field' => array(
      'title' => t('Node type flagger'),
      'help' => t('Flags a specific node type.'),
      'id' => 'node_type_flagger',
    ),
  );
*/  
  $data['node']['custom_calculated_field'] = array(
    'title' => t('Custom Calculated Field'),
    'field' => array(
      'title' => t('Custom Calculated Field'),
      'help' => t('Custom Field to Show Calculated Data'),
      'id' => 'custom_calculated_field',
    ),
  );  

  $data['node']['custom_player_points_field'] = array(
    'title' => t('Custom Player Points Calculated Field'),
    'field' => array(
      'title' => t('Custom Player Points Calculated Field'),
      'help' => t('Custom Field to Show Calculated Points'),
      'id' => 'custom_player_points_field',
    ),
  );
 }