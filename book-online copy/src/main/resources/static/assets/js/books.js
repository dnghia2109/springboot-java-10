const inputSearchElement = document.getElementById("input-search");

inputSearchElement.addEventListener("keydown", (e) => {
  if (e.keyCode == 13) {
    let url = new URL(window.location.href);
    let name = e.target.value.trim();
    if (name == "") {
      url.searchParams.delete("name");
    } else {
      url.searchParams.delete("page");
      url.searchParams.set("name", name);
    }
    window.location.href = url;
  }
});
