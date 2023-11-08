let fileInput = document.getElementById("file-input");
let imageContainer = document.getElementById("images");

function preview(){
    imageContainer.innerHTML = "";

    for(i of fileInput.files){
        let reader = new FileReader();
        let figure = document.createElement("figure");
        let figCap = document.createElement("figcaption");
        let img = document.createElement("img");
        let deleteButton = document.createElement("button");

        figCap.innerText = i.name;
        deleteButton.innerText = "Eliminar"
        deleteButton.addEventListener("click", () => {
            imageContainer.removeChild(figure);
        })

        figure.appendChild(img);
        figure.appendChild(figCap);
        figure.appendChild(deleteButton);
        reader.onload=()=>{
            img.setAttribute("src",reader.result);
        }
        imageContainer.appendChild(figure);
        reader.readAsDataURL(i);
    }
}