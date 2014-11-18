<?php include('include/misc/calendar-controller.php'); ?>
<table class="calendar large-12 medium-12">
  <thead>
    <tr class="navigation">
      <th class="prev-month"><a href="<?php echo htmlspecialchars($calendar->prev_month_url()) ?>"><?php echo $calendar->prev_month() ?></a></th>
      <th colspan="5" class="current-month"><?php echo $calendar->month() ?> <?php echo $calendar->year ?></th>
      <th class="next-month"><a href="<?php echo htmlspecialchars($calendar->next_month_url()) ?>"><?php echo $calendar->next_month() ?></a></th>
    </tr>
    <tr class="weekdays">
      <?php foreach ($calendar->days() as $day): ?>
        <th><?php echo $day ?></th>
      <?php endforeach ?>
    </tr>
  </thead>
  <tbody>
    <?php foreach ($calendar->weeks() as $week): ?>
      <tr>
        <?php foreach ($week as $day): ?>
          <?php
          list($number, $current, $data) = $day;
           
          $classes = array();
          $output  = '';
           
          if (is_array($data))
          {
            $classes = $data['classes'];
            $title   = $data['title'];
            $output  = empty($data['output']) ? '' : '<ul class="output"><li>'.implode('</li><li>', $data['output']).'</li></ul>';
          }
          ?>
          <td class="day <?php echo implode(' ', $classes) ?>">
            <span class="date" title="<?php echo implode(' / ', $title) ?>"><?php echo $number ?></span>
            <div class="day-content">
              <?php echo $output ?>
            </div>
          </td>
        <?php endforeach ?>
      </tr>
    <?php endforeach ?>
  </tbody>
</table>