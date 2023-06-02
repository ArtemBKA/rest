const urlUser = 'http://localhost:8080/user/info/'
let loggedInUser = document.querySelector('#UserInfo');

fetch(urlUser)
    .then(res => res.json())
    .then(data => {
        loggedInUser.innerHTML = `
                                <td>${data.id}</td>
                                <td>${data.firstName}</td>
                                <td>${data.lastName}</td>
                                <td>${data.age}</td>
                                <td>${data.email}</td>
                                <td>${data.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN').join(' ')}</td>
                                `;
    })