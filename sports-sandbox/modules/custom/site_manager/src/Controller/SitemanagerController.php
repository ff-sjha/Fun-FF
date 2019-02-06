<?php
/**
 * @file
 * @author Swati Jha
 * Contains \Drupal\example\Controller\ExampleController.
 * Please place this file under your example(module_root_folder)/src/Controller/
 */
namespace Drupal\site_manager\Controller;
//use Drupal\Core\Controller\ControllerBase;

/**
 * Provides route responses for the Example module.
 */
class SitemanagerController {
  /**
   * Returns a simple page.
   *
   * @return array
   *   A simple renderable array.
   */
  public function pointsPage() {
	  /*
    $element = array(
      '#markup' => 'Hello world!',
    );
    return $element;
	*/
	
	
    // Prepare _sortable_ table header
	/*
    $header = array(
        array('data' => t('ID'), 'field' => 'nid'),
        array('data' => t('Type'), 'field' => 'type'),
        array('data' => t('Lang'), 'field' => 'langcode', 'sort' => 'desc'),
    );

      $query = $this->database->select('node','loc');
      $query->fields('loc', array('nid', 'type', 'langcode'));
      $query->addField('loc','type','name_alias');
      $table_sort = $query->extend('Drupal\Core\Database\Query\TableSortExtender')->orderByHeader($header);
      $pager = $table_sort->extend('Drupal\Core\Database\Query\PagerSelectExtender')->limit(10);
      $result = $pager->execute();

      foreach($result as $row) {
          $rows[] = array('data' => (array) $row);
      }


      $build = array(
          '#markup' => t('List of All nodes')
      );

      $build['location_table'] = array(
        '#theme' => 'table', '#header' => $header,
          '#rows' => $rows
      );
      $build['pager'] = array(
       '#type' => 'pager'
      );	
	  */
	  
        $header = array(
            array('data' => t('ID'), 'field' => 'st.nid'),
            array('data' => t('Type'), 'field' => 'st.type'),
            array('data' => t('Lang'), 'field' => 'st.langcode'),
        );
        $query = db_select('node', 'st')
        ->fields('st', array('nid', 'type', 'langcode'))
		->condition('st.type', 'tournaments')
		->orderBy('st.nid', 'ASC')
        ->extend('Drupal\Core\Database\Query\TableSortExtender')
        ->extend('Drupal\Core\Database\Query\PagerSelectExtender')->limit(20)
        ->orderByHeader($header);
        $data = $query->execute();
		
        $rows = array();
        foreach ($data as $row) {
          $rows[] = array('data' => (array) $row);
        }
		
        $build['table_pager'][] = array(
            '#type' => 'table',
            '#header' => $header,
            '#rows' => $rows,
        );
        $build['table_pager'][] = array(
            '#type' => 'pager',
        );	  
	  
	  return $build;
  }
}
?>