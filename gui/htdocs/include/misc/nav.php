<ul <?php echo $offcanvas===TRUE? 'class="off-canvas-list"' : 'class="side-nav accordion" data-accordion"' ?>>
  <li>
    <a href="/">
      <label>
        Schiller
      </label>
    </a>
  </li>
  <li>
    <a href="history">
      Firmengeschichte
    </a>
  </li>
  <li>
    <a href="contact">
      Kontakt
    </a>
  </li>
  <li>
    <a href="/calendar">
      Kalender
    </a>
  </li>
  <li <?php echo $offcanvas===TRUE? 'class="has-submenu"': 'class="accordion-navigation"' ?>>
    <a href="#books">
      Bücher                  
    </a>
    <ul <?php echo $offcanvas===TRUE? 'class="left-submenu"' : 'id="books" class="content"' ?>>
      <?php if($offcanvas === TRUE): ?>
      <li class="back">
        <a href="#">
          Zurück
        </a>
      </li>
      <li>
        <label>
          Kategorien
        </label>
      </li>
      <?php endif; ?>
      <li>
        <a href="book-categories/horror">
          Horror
        </a>
      </li>
      <li>
        <a href="book-categories/thriller">
          Thriller
        </a>
      </li>
      <li>
        <a href="book-categories/factual-book">
          Factual Book
        </a>
      </li>
      <li>
        <a href="book-categories/romance">
          Romance
        </a>
      </li>
      <li>
        <a href="book-categories/sci-fi">
          Sci-Fi
        </a>
      </li>
      <li>
        <a href="book-categories/fantasy">
          Fantasy
        </a>
      </li>
      <li>
        <a href="book-categories/crime">
          Crime
        </a>
      </li>
      <li>
        <a href="book-categories/guide">
          Guide
        </a>
      </li>
      <li>
        <a href="book-categories/erotic">
          Erotic
        </a>
      </li>
      <li>
        <a href="book-categories/youth">
          Youth
        </a>
      </li>
    </ul>
  </li>
  <li <?php echo $offcanvas===TRUE? 'class="has-submenu"': 'class="accordion-navigation"' ?>>
    <a href="#cd">
      CD                  
    </a>
    <ul <?php echo $offcanvas===TRUE? 'class="left-submenu"' : 'id="cd" class="content"' ?>>
      <?php if($offcanvas === TRUE): ?>
      <li class="back">
        <a href="#">
          Zurück
        </a>
      </li>
      <li>
        <label>
          Kategorien
        </label>
      </li>
      <?php endif; ?>
      <li>
        <a href="cd-categories/horror">
          Horror
        </a>
      </li>
      <li>
        <a href="cd-categories/thriller">
          Thriller
        </a>
      </li>
      <li>
        <a href="cd-categories/factual-book">
          Factual Book
        </a>
      </li>
      <li>
        <a href="cd-categories/romance">
          Romance
        </a>
      </li>
      <li>
        <a href="cd-categories/sci-fi">
          Sci-Fi
        </a>
      </li>
      <li>
        <a href="cd-categories/fantasy">
          Fantasy
        </a>
      </li>
      <li>
        <a href="cd-categories/crime">
          Crime
        </a>
      </li>
      <li>
        <a href="cd-categories/guide">
          Guide
        </a>
      </li>
      <li>
        <a href="cd-categories/erotic">
          Erotic
        </a>
      </li>
      <li>
        <a href="cd-categories/youth">
          Youth
        </a>
      </li>
    </ul>
  </li>
  <li <?php echo $offcanvas===TRUE? 'class="has-submenu"': 'class="accordion-navigation"' ?>>
    <a href="#dvd">
      DVD                  
    </a>
    <ul <?php echo $offcanvas===TRUE? 'class="left-submenu"' : 'id="dvd" class="content"' ?>>
      <?php if($offcanvas === TRUE): ?>
      <li class="back">
        <a href="#">
          Zurück
        </a>
      </li>
      <li>
        <label>
          Kategorien
        </label>
      </li>
      <?php endif; ?>
      <li>
        <a href="dvd-categories/horror">
          Horror
        </a>
      </li>
      <li>
        <a href="dvd-categories/thriller">
          Thriller
        </a>
      </li>
      <li>
        <a href="dvd-categories/factual-book">
          Factual Book
        </a>
      </li>
      <li>
        <a href="dvd-categories/romance">
          Romance
        </a>
      </li>
      <li>
        <a href="dvd-categories/sci-fi">
          Sci-Fi
        </a>
      </li>
      <li>
        <a href="dvd-categories/fantasy">
          Fantasy
        </a>
      </li>
      <li>
        <a href="dvd-categories/crime">
          Crime
        </a>
      </li>
      <li>
        <a href="dvd-categories/guide">
          Guide
        </a>
      </li>
      <li>
        <a href="dvd-categories/erotic">
          Erotic
        </a>
      </li>
      <li>
        <a href="dvd-categories/youth">
          Youth
        </a>
      </li>
    </ul>
  </li>
</ul>