function geturl(url,body=""){
    console.log("received get request");
    console.log(url);
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken.token}`,
        },
    }).then(response => { console.log(response.body)})
}
function posturl(url,body=""){
    console.log("received pst request")
    fetch(url, {
        method: 'GET',
        body: body,
        headers: {
            'Authorization': `Bearer ${jwtToken.token}`,
        },
    }) .then(response => { console.log(response)})
}