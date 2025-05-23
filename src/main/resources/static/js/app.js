// const menuToggle = document.getElementById('menu-toggle')
// const mobileMenu = document.getElementById('mobile-menu')
// menuToggle.addEventListener('click', () => {
//   mobileMenu.classList.toggle('hidden')
// })

// Wrap all JavaScript code in DOMContentLoaded
document.addEventListener('DOMContentLoaded', () => {
  // Mobile Menu Toggle - Single initialization
  const menuToggle = document.getElementById('menu-toggle')
  const mobileMenu = document.getElementById('mobile-menu')

  if (menuToggle && mobileMenu) {
    menuToggle.addEventListener('click', () => {
      mobileMenu.classList.toggle('hidden')

      // Optional: Add animation class
      mobileMenu.classList.toggle('animate-slide-down')

      // Optional: Close menu when clicking outside
      document.addEventListener('click', e => {
        if (!menuToggle.contains(e.target) && !mobileMenu.contains(e.target)) {
          mobileMenu.classList.add('hidden')
        }
      })
    })
  }

  // Remove duplicate mobile menu toggle code from other sections
  // ... rest of your existing JavaScript code ...
})

// Profile JS:
// Profile Edit Functionality
const editProfileBtn = document.getElementById('editProfileBtn')
const closeProfileModalBtn = document.getElementById('closeProfileModalBtn')
const cancelProfileModalBtn = document.getElementById('cancelProfileModalBtn')
const editProfileModal = document.getElementById('editProfileModal')
const profileForm = document.getElementById('profileForm')
const profileImageInput = document.getElementById('profileImage')
const profileImagePreview = document.getElementById('profileImagePreview')

// Open Edit Modal
editProfileBtn.addEventListener('click', () => {
  // Populate form fields with current data
  document.getElementById('fullName').value =
    document.getElementById('profileName').textContent
  document.getElementById('bio').value =
    document.getElementById('profileBio').textContent
  document.getElementById('email').value =
    document.getElementById('profileEmail').textContent
  document.getElementById('university').value =
    document.getElementById('profileUniversity').textContent

  editProfileModal.classList.remove('hidden')
  document.body.style.overflow = 'hidden'
})

// Handle Form Submission
profileForm.addEventListener('submit', e => {
  e.preventDefault()

  // Update profile information
  document.getElementById('profileName').textContent =
    document.getElementById('fullName').value
  document.getElementById('profileBio').textContent =
    document.getElementById('bio').value
  document.getElementById('profileEmail').textContent =
    document.getElementById('email').value
  document.getElementById('profileUniversity').textContent =
    document.getElementById('university').value

  editProfileModal.classList.add('hidden')
  document.body.style.overflow = 'auto'
})

// Handle Image Upload
profileImageInput.addEventListener('change', e => {
  const file = e.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = event => {
      profileImagePreview.src = event.target.result
      document.getElementById('profileImageDisplay').src = event.target.result
    }
    reader.readAsDataURL(file)
  }
})

// Close Modal Handlers
;[closeProfileModalBtn, cancelProfileModalBtn].forEach(btn => {
  btn.addEventListener('click', () => {
    editProfileModal.classList.add('hidden')
    document.body.style.overflow = 'auto'
  })
})

editProfileModal.addEventListener('click', e => {
  if (e.target === editProfileModal) {
    editProfileModal.classList.add('hidden')
    document.body.style.overflow = 'auto'
  }
})

menuToggle.addEventListener('click', () => {
  mobileMenu.classList.toggle('hidden')
})

// Material modal functionality
const addMaterialBtn = document.getElementById('addMaterialBtn')
const closeModalBtn = document.getElementById('closeModalBtn')
const cancelModalBtn = document.getElementById('cancelModalBtn')
const addMaterialModal = document.getElementById('addMaterialModal')

addMaterialBtn.addEventListener('click', () => {
  addMaterialModal.classList.remove('hidden')
  document.body.style.overflow = 'hidden'
})

closeModalBtn.addEventListener('click', () => {
  addMaterialModal.classList.add('hidden')
  document.body.style.overflow = 'auto'
})

cancelModalBtn.addEventListener('click', () => {
  addMaterialModal.classList.add('hidden')
  document.body.style.overflow = 'auto'
})

// Close modal when clicking outside
addMaterialModal.addEventListener('click', e => {
  if (e.target === addMaterialModal) {
    addMaterialModal.classList.add('hidden')
    document.body.style.overflow = 'auto'
  }
})

// File upload preview (for cover image)
const coverImageInput = document.getElementById('coverImage')
coverImageInput.addEventListener('change', e => {
  const file = e.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = event => {
      // You can add preview functionality here if needed
    }
    reader.readAsDataURL(file)
  }
})

// File upload preview (for material file)
const materialFileInput = document.getElementById('materialFile')
materialFileInput.addEventListener('change', e => {
  const file = e.target.files[0]
  if (file) {
    // You can add file validation here
    console.log('Selected file:', file.name, file.size)
  }
})

function openModal (id) {
  document.getElementById(id).classList.remove('hidden')
}
function closeModal (id) {
  document.getElementById(id).classList.add('hidden')
}

// Discussion JS
function toggleReply (button) {
  const replyBox = button.closest('.flex').parentElement.nextElementSibling
  replyBox.classList.toggle('hidden')
}

function toggleReplies (button) {
  const replies = button
    .closest('.flex')
    .parentElement.parentElement.querySelector('.replies')
  replies.classList.toggle('hidden')
  button.textContent = replies.classList.contains('hidden')
    ? 'Show Replies'
    : 'Hide Replies'
}

// Help JS

// Tab and form elements
const loginForm = document.getElementById('login-form')
const registerForm = document.getElementById('register-form')
const loginTab = document.getElementById('login-tab')
const registerTab = document.getElementById('register-tab')
const formError = document.getElementById('form-error')

// Switch to Register Tab
function switchToRegister () {
  loginForm.classList.add('hidden')
  registerForm.classList.remove('hidden')
  loginTab.classList.remove('text-blue-600', 'border-blue-600')
  loginTab.classList.add('text-gray-600', 'border-transparent')
  loginTab.setAttribute('aria-selected', 'false')
  loginTab.setAttribute('tabindex', '-1')
  registerTab.classList.add('text-blue-600', 'border-blue-600')
  registerTab.classList.remove('text-gray-600', 'border-transparent')
  registerTab.setAttribute('aria-selected', 'true')
  registerTab.setAttribute('tabindex', '0')
  formError.classList.add('hidden')
  registerTab.focus()
}

// Switch to Login Tab
function switchToLogin () {
  registerForm.classList.add('hidden')
  loginForm.classList.remove('hidden')
  registerTab.classList.remove('text-blue-600', 'border-blue-600')
  registerTab.classList.add('text-gray-600', 'border-transparent')
  registerTab.setAttribute('aria-selected', 'false')
  registerTab.setAttribute('tabindex', '-1')
  loginTab.classList.add('text-blue-600', 'border-blue-600')
  loginTab.classList.remove('text-gray-600', 'border-transparent')
  loginTab.setAttribute('aria-selected', 'true')
  loginTab.setAttribute('tabindex', '0')
  formError.classList.add('hidden')
  loginTab.focus()
}

// Keyboard navigation for tabs
loginTab.addEventListener('keydown', e => {
  if (e.key === 'ArrowRight' || e.key === 'ArrowLeft') {
    e.preventDefault()
    switchToRegister()
  }
})
registerTab.addEventListener('keydown', e => {
  if (e.key === 'ArrowLeft' || e.key === 'ArrowRight') {
    e.preventDefault()
    switchToLogin()
  }
})

loginTab.addEventListener('click', switchToLogin)
registerTab.addEventListener('click', switchToRegister)

// Password visibility toggle
function togglePassword (inputId, btn) {
  const input = document.getElementById(inputId)
  if (input.type === 'password') {
    input.type = 'text'
    btn
      .querySelector('svg path')
      .setAttribute(
        'd',
        'M13.875 18.825A10.05 10.05 0 0112 19c-5 0-9-4-9-7s4-7 9-7c1.657 0 3.216.417 4.563 1.125M19.07 4.93l-14.14 14.14M15 12a3 3 0 11-6 0 3 3 0 016 0z'
      )
  } else {
    input.type = 'password'
    btn
      .querySelector('svg path')
      .setAttribute(
        'd',
        'M15 12a3 3 0 11-6 0 3 3 0 016 0zm6 0a9 9 0 11-18 0 9 9 0 0118 0z'
      )
  }
}

// Form validation helpers
function showError (message) {
  formError.textContent = message
  formError.classList.remove('hidden')
}
function clearError () {
  formError.textContent = ''
  formError.classList.add('hidden')
}

// Login form validation
loginForm.addEventListener('submit', function (e) {
  e.preventDefault()
  clearError()
  const email = document.getElementById('login-email').value.trim()
  const password = document.getElementById('login-password').value
  if (!email || !password) {
    showError('Please enter both email and password.')
    return
  }
  if (!validateEmail(email)) {
    showError('Please enter a valid email address.')
    return
  }
  // TODO: Replace with actual login logic
  alert('Login successful!\nEmail: ' + email)
  loginForm.reset()
})

// Register form validation
registerForm.addEventListener('submit', function (e) {
  e.preventDefault()
  clearError()
  const name = document.getElementById('register-name').value.trim()
  const email = document.getElementById('register-email').value.trim()
  const password = document.getElementById('register-password').value
  const confirm = document.getElementById('register-confirm').value
  const college = document.getElementById('register-college').value.trim()
  const course = document.getElementById('register-course').value.trim()
  const year = document.getElementById('register-year').value

  if (
    !name ||
    !email ||
    !password ||
    !confirm ||
    !college ||
    !course ||
    !year
  ) {
    showError('Please fill in all fields.')
    return
  }
  if (!validateEmail(email)) {
    showError('Please enter a valid email address.')
    return
  }
  if (password.length < 6) {
    showError('Password must be at least 6 characters.')
    return
  }
  if (password !== confirm) {
    showError('Passwords do not match.')
    return
  }
  // TODO: Replace with actual registration logic
  alert(
    'Registration successful!\nWelcome, ' +
      name +
      '!\nCollege: ' +
      college +
      '\nCourse: ' +
      course +
      '\nYear: ' +
      (year === 'other'
        ? 'Other'
        : year +
          (year == 1 ? 'st' : year == 2 ? 'nd' : year == 3 ? 'rd' : 'th') +
          ' Year')
  )
  registerForm.reset()
  switchToLogin()
})

// Email validation
function validateEmail (email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}

// Material JS

// Mobile menu toggle
document
  .getElementById('mobile-menu-button')
  .addEventListener('click', function () {
    const menu = document.getElementById('mobile-menu')
    menu.classList.toggle('hidden')
  })

// Download button loader example
document.querySelectorAll('[data-download]').forEach(button => {
  button.addEventListener('click', function (e) {
    const spinner = this.querySelector('.download-spinner')
    spinner.classList.remove('hidden')
    this.disabled = true

    // Simulate download delay
    setTimeout(() => {
      spinner.classList.add('hidden')
      this.disabled = false
    }, 1500)
  })
})

// Upload Material JS

// File upload interaction
const fileInput = document.getElementById('materialFile')
const fileInfo = document.getElementById('file-info')
const progressBar = document.querySelector('.progress-bar')
const uploadProgress = document.querySelector('.upload-progress')

fileInput.addEventListener('change', function (e) {
  const file = e.target.files[0]
  if (file) {
    fileInfo.classList.remove('hidden')
    fileInfo.innerHTML = `
          <span class="font-medium">${file.name}</span>
          <span class="text-gray-500 ml-2">${(file.size / 1024 / 1024).toFixed(
            2
          )}MB</span>
        `

    // Simulate upload progress
    uploadProgress.classList.remove('hidden')
    let progress = 0
    const interval = setInterval(() => {
      progress += Math.random() * 10
      progressBar.style.width = `${Math.min(progress, 100)}%`
      if (progress >= 100) clearInterval(interval)
    }, 200)
  }
})

// Drag and drop functionality
const dropZone = document.querySelector('.file-upload')

;['dragenter', 'dragover'].forEach(event => {
  dropZone.addEventListener(event, e => {
    e.preventDefault()
    dropZone.classList.add('border-blue-500', 'bg-blue-50')
  })
})
;['dragleave', 'drop'].forEach(event => {
  dropZone.addEventListener(event, e => {
    e.preventDefault()
    dropZone.classList.remove('border-blue-500', 'bg-blue-50')
  })
})

dropZone.addEventListener('drop', e => {
  const files = e.dataTransfer.files
  fileInput.files = files
  fileInput.dispatchEvent(new Event('change'))
})
