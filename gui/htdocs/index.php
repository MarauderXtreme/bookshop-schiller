<?php
  session_start();
  include('include/config/content.inc');
  //include('template/config.php');   
  
  
  if(!isset($_GET['q']) || empty($_GET['q']) || !array_key_exists($_GET['q'],$contents)) {
    $content = "home";
  }
  else {
    $content = $_GET['q'];
  }
  
?>
<!doctype html>
  <html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>
      <?php echo $contents[$content][2]? $contents[$content][2] . ' | ' : '';  ?>
      Buchhandlung Schiller
    </title>
    
    <link rel="stylesheet" type="text/css" href="/css/foundation.css" />
    <link rel="stylesheet" type="text/css" href="/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/css/slick.css" />
    <link rel="stylesheet" type="text/css" href="/include/misc/php-calendar/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/css/style.css" />
    
    <script type="text/javascript" src="/js/vendor/modernizr.js"></script>
    
  </head>
  <body>
    <div class="off-canvas-wrap" data-offcanvas>
      <div class="fixed">
        <nav class="top-bar tab-bar" data-topbar role="navigation">
          <section class="left-small show-for-small-only">  <!-- -->
            <a class="left-off-canvas-toggle menu-icon" aria-expanded="false">
              <span></span>
            </a>
          </section>
          <section class="right tab-bar-section">
            <h2 class="title">
              <?php echo $contents[$content][2]? $contents[$content][2] . ' | ' : ''; ?>Buchhandlung Schiller
            </h2>
          </section>
        </nav>
      </div>
      <div class="inner-wrap">
        <aside class="left-off-canvas-menu" aria-hidden="true">
          <?php
            $offcanvas = TRUE;
            include('include/misc/nav.php');
          ?>
        </aside>
        <div class="main-content">
          <div class="large-12">&nbsp;</div>
          <div class="large-12">&nbsp;</div>
          <header class="large-12 medium-12 show-for-landscape show-for-medium-up">
            <div class="panel">
              <?php include('include/misc/' . $contents[$content][0] . '.php'); ?>
            </div>
          </header>
          <nav class="large-12 breadcrumbs" role="menubar" aria-label="breadcrumbs">
            <?php include('include/misc/breadcrumbs.php'); ?>
          </nav>
          <div class="large-12">&nbsp;</div>
          <nav class="large-2 medium-2 columns hide-on-small">
            <?php 
              $offcanvas = FALSE;
              include('include/misc/nav.php');
            ?>
          </nav>
          <div class="large-10 medium-10 small-12 columns">
            <?php 
              if(is_file('include/content/' . $contents[$content][1] . '.php')) {
                include('include/content/' . $contents[$content][1] . '.php');
              }
            ?>
          </div>
        </div>
        <a class="exit-off-canvas"></a>
      </div>
    </div>
    <script type="text/javascript" src="/js/vendor/jquery.js"></script>
    <script type="text/javascript" src="/js/vendor/fastclick.js"></script>
    <script type="text/javascript" src="/js/foundation.min.js"></script>
    <script type="text/javascript" src="/js/foundation/foundation.accordion.js"></script>
    <script type="text/javascript" src="/js/vendor/slick.min.js"></script>
    <script type="text/javascript">
      $(document).foundation(
        {
          accordion: {
            content-class: 'content',
            active-class: 'active',
            toggleable: true
          }
        }
      );
    </script>
    <script type="text/javascript">  
      $(document).ready(function(){
        $('.header-slider').slick({
          dots: true,
          infinite: true,
          lazyLoad: 'ondemand',
          slidesToShow: 1,
          slidesToScroll: 1,
          autoplay: true,
          autoplaySpeed: 3000,
        });
      });
    </script>
  </body>
</html>
