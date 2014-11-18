<?php
  include 'include/misc/php-calendar/classes/calendar.php';
   
  $month = isset($_GET['m']) ? $_GET['m'] : NULL;
  $year  = isset($_GET['y']) ? $_GET['y'] : NULL;
   
  $calendar = Calendar::factory($month, $year);
   
  $event1 = $calendar->event()
    ->condition('timestamp', strtotime(date('F').' 21, '.date('Y')))
    ->title('Lesung - Es')
    ->output('<a data-tooltip aria-haspopup="true" class="has-tip" title="Lesung von Stephen King. Sein neues Buch Es. Anschließend Autogrammstunde" href="reading/21-11-14/1">Stephen King - Es</a>');
       
  $event2 = $calendar->event()
    ->condition('timestamp', strtotime(date('F').' 21, '.date('Y')))
    ->title('Buchbesprechung - Er ist wieder da')
    ->output('<a data-tooltip aria-haspopup="true" class="has-tip" title="Besprechung des kontroversen Buches. Timur Vermes steht Rede und Antwort." href="reading/21-11-14/2">Buchbesprechung - Er ist wieder da</a>');
  
  $event3 = $calendar->event()
    ->condition('timestamp', strtotime(date('F').' 13, '.date('Y')))
    ->title('Operator Birthday')
    ->output('<a data-tooltip aria-haspopup="true" class="has-tip" title="Der Bastard hat Geburtstag." href="reading/13-11-14/1">Operator Birthday</a>');
  
  $calendar->standard('today')
    ->standard('prev-next')
    ->standard('holidays')
    ->attach($event1)
    ->attach($event2)
    ->attach($event3);
?>