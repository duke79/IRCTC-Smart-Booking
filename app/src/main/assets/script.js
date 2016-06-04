// Intialization for web testing
/*
Android = {
showToast: function(str)
{
console.log("TOAST:"+str);
},
putOnConsole: function(str)
{
console.log("Console:"+str);
}
}
*/

/*
function getBase64Image(img) {
    // Create an empty canvas element
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;

    // Copy the image contents to the canvas
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0);

    // Get the data-URL formatted image
    // Firefox supports PNG and JPEG. You could check img.src to
    // guess the original format, but be aware the using "image/jpg"
    // will re-encode the image.
    var dataURL = canvas.toDataURL("image/png");

    return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
};
*/

function confirmInitialized(parent,varstring)
{
    var arr = varstring.split(".");
    var newvarstring="";
    for(i=1;i<arr.length;i++)
    {
      newvarstring+=arr[i];
      if(i != arr.length-1)
      {
        newvarstring+=".";
      }
    }
    if(typeof parent[arr[0]] == 'undefined')
    {
      parent[arr[0]]={}
    }

    if(arr.length>1)
    confirmInitialized(parent[arr[0]],newvarstring);
}

var checkExist = setInterval(function() {
   confirmInitialized(Android,"irctc");
   if(typeof Android.irctc.imgcaptcha=='undefined')
   {
     if ($('#cimage').length)
     {
       Android.showToast("Exists!");
       Android.irctc.imgcaptcha=$('#cimage')[0];
       clearInterval(checkExist);
     }
   }
   else
   {
     clearInterval(checkExist);
   }
}, 100); // check every 100ms

Android.showToast("Hello Hello");
