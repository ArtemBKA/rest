const url = 'http://localhost:8080/admin/users/';
const urlRoles = 'http://localhost:8080/admin/roles/';

const container = document.querySelector('.usersTbody');
const newUserForm = document.getElementById('newUserForm');
const editUserForm = document.getElementById('editUserForm');
const deleteUserForm = document.getElementById('deleteUserForm');
const btnCreate = document.getElementById('new-user-tab');

let rolesArr = [];

const editId = document.getElementById('editId');
const editFirstName = document.getElementById('editFirstName');
const editLastName = document.getElementById('editLastName');
const editAge = document.getElementById('editAge');
const editEmail = document.getElementById('editEmail');
const editPassword = document.getElementById('editPassword');
const editRoles = document.getElementById('editRoles');

const delId = document.getElementById('delId');
const delFirstName = document.getElementById('delFirstName');
const delLastName = document.getElementById('delLastName');
const delAge = document.getElementById('delAge');
const delEmail = document.getElementById('delEmail');
const delRoles = document.getElementById('delRoles');

const newFirstName = document.getElementById('newFirstName');
const newLastName = document.getElementById('newLastName');
const newAge = document.getElementById('newAge');
const newEmail = document.getElementById('newEmail');
const newPassword = document.getElementById('newPassword');
const newRoles = document.getElementById('newRoles');

const editUserModal = new bootstrap.Modal(document.getElementById('editUserModal'));
const deleteUserModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

const renderUsers = (users) => {
    let result = users.reduce((acc, user) => {
        let roles = '';
        user.roles.forEach(role => {
            const r = role.name.substring(5);
            roles += r + ' ';
        });
        return acc + `
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td hidden>${user.password}</td>
                <td>${roles}</td>
                <td><a class="btnEdit btn btn-sm btn-info text-white">Edit</a></td>
                <td><a class="btnDelete btn btn-danger btn-sm">Delete</a></td>
            </tr>`;
    }, '');
    container.innerHTML = result;
}

const renderRoles = (roles) => {
    const rolesOptions = roles.reduce((acc, role) => {
        rolesArr.push(role);
        return acc + `<option value="${role.id}">${role.name.substring(5)}</option>`
    }, '');
    newRoles.innerHTML = editRoles.innerHTML = delRoles.innerHTML = rolesOptions;
}

on(document, 'click', '.btnDelete', e => {
    const row = e.target.parentNode.parentNode;
    const idForm = row.children[0].innerHTML;
    [delFirstName.value, delLastName.value, delAge.value, delEmail.value, delRoles.value] =
        [1, 2, 3, 4, 6].map(i => row.children[i].innerHTML);
    delId.value = idForm;
    deleteUserModal.show();
})

on(document, 'click', '.btnEdit', e => {
    const row = e.target.parentNode.parentNode;
    const idForm = row.children[0].innerHTML;
    [editFirstName.value, editLastName.value, editAge.value, editEmail.value, editPassword.value] =
        [1, 2, 3, 4, 5].map(i => row.children[i].innerHTML);
    editId.value = idForm;
    editRoles.options.selectedIndex = -1;
    editUserModal.show();
})

btnCreate.addEventListener('click', () => {
    [newFirstName.value, newLastName.value, newAge.value, newEmail.value, newPassword.value] = ['', '', '', '', ''];
    newRoles.options.selectedIndex = -1;
});

deleteUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url + delId.value, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
    })
        .then(res => res.json())
        .catch(err => console.log(err))
        .then(refreshListOfUsers);
    deleteUserModal.hide();
});

newUserForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const selectedOpts = [...newRoles.options]
        .filter(x => x.selected)
        .map(x => x.value);
    const rolesJ = selectedOpts.map(role => rolesArr[role - 1]);
    const body = JSON.stringify({
        firstName: newFirstName.value,
        lastName: newLastName.value,
        age: newAge.value,
        email: newEmail.value,
        password: newPassword.value,
        roles: rolesJ
    });
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body
        });
        if (!response.ok) {
            const errorData = await response.json();
            alert(errorData.message);
        } else {
            refreshListOfUsers();
            const [navtab1, navtab2] = [document.getElementById('all-users-tab'), document.getElementById('new-user-tab')];
            const [tab1, tab2] = [document.getElementById('all-users'), document.getElementById('new-user')];
            navtab1.setAttribute("class", "nav-link active");
            navtab2.setAttribute("class", "nav-link");
            tab1.setAttribute("class", "tab-pane fade active show");
            tab2.setAttribute("class", "tab-pane fade");
        }
    } catch (error) {
        console.log(error);
    }
});

editUserForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const idForm = editId.value;
    const selectedOpts = [...editRoles.options]
        .filter(x => x.selected)
        .map(x => x.value);
    const rolesJ = selectedOpts.map(role => rolesArr[role - 1]);
    const body = JSON.stringify({
        id: idForm,
        firstName: editFirstName.value,
        lastName: editLastName.value,
        age: editAge.value,
        email: editEmail.value,
        password: editPassword.value,
        roles: rolesJ
    });
    try {
        const response = await fetch(url + idForm, {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json'},
            body
        });
        if (!response.ok) {
            const errorData = await response.json();
            alert(errorData.message);
        } else {
            refreshListOfUsers();
            editUserModal.hide();
        }
    } catch (error) {
        console.log(error);
    }
});

const refreshListOfUsers = async () => {
    try {
        const res = await fetch(url);
        const data = await res.json();
        renderUsers(data);
    } catch (error) {
        console.log(error);
    }
};

const init = async () => {
    try {
        const [usersRes, rolesRes] = await Promise.all([fetch(url), fetch(urlRoles)]);
        const [usersData, rolesData] = await Promise.all([usersRes.json(), rolesRes.json()]);
        renderUsers(usersData);
        renderRoles(rolesData);
    } catch (error) {
        console.log(error);
    }
};

init();