<?php
// Report all PHP errors (see changelog)
error_reporting(E_ALL);
ini_set('display_errors', 1);
include("database.php");

$pathparams = array_slice( explode('/', strtok(getenv('REQUEST_URI'), '?')), 3 );
$bookid = $pathparams[0];
$chapterNo = $pathparams[1];
$verseNo = $pathparams[2];
$script = $_GET["script"];

$db = new Database();
$result = $db->query("SELECT * FROM ved.sutra where book_id=$bookid and chapter_no=$chapterNo and verse_no=$verseNo and lang_code='$script'");
if ($result) {
    while ($obj = $result->fetch_object()) {
        echo "<p>".$obj->content."</p>";
    }
} else {
    echo "not found";
}


$db->close(); 
?>