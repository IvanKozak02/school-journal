

document.getElementById("save-certificate").addEventListener("click",()=>{
    let inputMultipartFiles = document.getElementById("add-certificate");
    if (inputMultipartFiles.files.length !==0){
        saveCertificates(inputMultipartFiles);
    }
    else {
        if (document.querySelector('[db_id]').length === 0) {
            document.getElementById("remove-sertificate").style.display = "none";
        }
    }


})


async function saveCertificates(inputMultipartFiles){
    const formData = new FormData();

    for (let i = 0; i < inputMultipartFiles.files.length; i++) {
        formData.append("file",inputMultipartFiles.files[i])
    }

    console.log(formData);

    let certificates;
    let safedCertificates = fetch("http://localhost:8080/upload-file",{
        method: 'POST',
        headers: {Accept: 'application/json' },
        body: formData
    })
        .then(res=>{
            return res.json();
        })

    certificates = await safedCertificates;
    addImageOfCertificatesToSlider(certificates);


}


function addImageOfCertificatesToSlider(images){
    removeSliders();
    for (let i = 0; i < images.length; i++) {
        document.querySelector(".sliderbig").innerHTML += `<div db_id="${images[i].id}" id="big" class="sliderbig__items"><img src="img/${images[i].imgUrl}" alt=""></div>`
        document.querySelector(".slider").innerHTML += `<div db_id="${images[i].id}" class="slider__items"><img src="img/${images[i].imgUrl}" alt=""></div>`

    }
    slickCarousel()
    document.getElementById("add-certificate").value=""
    document.getElementById("remove-sertificate").style.display = "block";


}

function removeSliders(){
    const myNodeBig = document.querySelector(".sliderbig");
    myNodeBig.innerHTML = "";
    const myNodeSmall = document.querySelector(".slider");
    myNodeSmall.innerHTML = "";
    document.querySelector(".sliderbig").classList.remove("slick-initialized");
    document.querySelector(".sliderbig").classList.remove("slick-slider");
    document.querySelector(".slider").classList.remove("slick-initialized");
    document.querySelector(".slider").classList.remove("slick-slider");
}

function slickCarousel() {

    $(".sliderbig").slick({
        arrows:false,
        slidesToShow: 1,
        asNavFor: ".slider",
        centerMode:true,

    })
    $(".slider").slick({

        slidesToShow: 1,
        slidesToScroll: 1,
        dots:true,
        adaptiveHeight:true,
        centerMode:true,
        asNavFor:".sliderbig"
    });

}


document.getElementById("remove-sertificate").addEventListener("click", ()=>{
    let deleteCertificateSl1 = document.querySelector(".sliderbig__items.slick-slide.slick-current.slick-active.slick-center");
    let deleteCertificateSl2 = document.querySelector(".slider__items.slick-slide.slick-current.slick-active.slick-center");
    let certId = deleteCertificateSl1.getAttribute("db_id");

    let nextCert = parseInt(certId) + 1;
    let delete_cert = fetch("http://localhost:8080/deletecert?certId=" + `${certId}`, {
        method:"DELETE",
        body:certId
    }).then(res=>{
        if (res.ok && document.querySelectorAll(".sliderbig__items").length > 1){
            getAllCert();
        }
        else {
            removeSliders();
            document.getElementById("remove-sertificate").style.display = "none";
        }
    })
})

async function getAllCert(){
    let getCert = fetch("http://localhost:8080/allcertificate",{
        method: 'POST',
        headers: {Accept: 'application/json' }
    })
        .then(res=>{
            return res.json();
        })
    let certificates = await getCert;
    removeCertificates(certificates)
}

function removeCertificates(cert){
    removeSliders();
        for (let i = 0; i < cert.length; i++) {
            document.querySelector(".sliderbig").innerHTML += `<div db_id="${cert[i].id}" id="big" class="sliderbig__items"><img src="img/${cert[i].imgUrl}" alt=""></div>`
            document.querySelector(".slider").innerHTML += `<div db_id="${cert[i].id}" class="slider__items"><img src="img/${cert[i].imgUrl}" alt=""></div>`

        }
        slickCarousel()
        document.getElementById("add-certificate").value=""
    }

