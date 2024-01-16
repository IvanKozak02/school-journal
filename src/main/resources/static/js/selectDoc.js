const selectImage = document.querySelector('.select-image');
const inputFile = document.querySelector('#file');
const imgArea = document.querySelector('.img-area');

selectImage.addEventListener('click', function () {
    inputFile.click();
})

inputFile.addEventListener('change', function () {
    const image = this.files[0]
    if(image.size < 2000000) {
        const reader = new FileReader();
        reader.onload = ()=> {
            const allImage = imgArea.querySelectorAll('img');
            allImage.forEach(item=> item.remove());
            const imageUrl = reader.result;
            const img = document.createElement('img');
            img.src = imageUrl;
            img.id = "img";
            imgArea.appendChild(img);
            imgArea.classList.add('active');
            imgArea.dataset.img = image.name;
        }
        reader.readAsDataURL(image);
    } else {
        alert("Розмір зображення не може перевищувати 2МБ");
    }
})