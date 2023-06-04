const urlUser = 'http://localhost:8080/user/info/';
let loggedInUser = document.querySelector('#UserInfo');
let loggedUserEmail = document.querySelector('#EmailInfo');
let loggedUserRoles = document.querySelector('#RolesInfo');

function getUserData() {
    fetch(urlUser)
        .then(res => res.json())
        .then(data => {
            loggedInUser.innerHTML = `
        <td>${data.id}</td>
        <td>${data.firstName}</td>
        <td>${data.lastName}</td>
        <td>${data.age}</td>
        <td>${data.email}</td>
        <td>${data.roles
                .map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')
                .sort((a, b) => a.localeCompare(b))
                .join(' ')
            }</td>
      `;

            loggedUserEmail.innerHTML = `
        <td>${data.email}</td>
      `;

            loggedUserRoles.innerHTML = `
        <td>${data.roles
                .map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')
                .sort((a, b) => a.localeCompare(b))
                .join(' ')
            }</td>
      `;
        })
        .catch(error => console.error(error));
}

getUserData();

setInterval(() => {
    getUserData();
}, 100);