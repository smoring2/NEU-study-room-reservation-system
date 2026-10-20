import { describe, it, expect } from 'vitest'
import { isvalidUsername, validateURL, validateLowerCase, validateUpperCase, validatAlphabets } from '@/utils/validate'
import { parseTime, formatTime, isExternal } from '@/utils'
import { statusFilter } from '@/utils/filters'
import { getToken, setToken, removeToken } from '@/utils/auth'

// Covers business rules VAL-01, VAL-02, DISP-14, AUTH-01 and DISP-03.
describe('utilities and the filter-to-function migration', () => {
  describe('validation rules', () => {
    it('VAL-01: only admin and editor are valid usernames, surrounding spaces ignored', () => {
      expect(isvalidUsername('admin')).toBe(true)
      expect(isvalidUsername('editor')).toBe(true)
      expect(isvalidUsername('  admin  ')).toBe(true)
      expect(isvalidUsername('nobody')).toBe(false)
      expect(isvalidUsername('')).toBe(false)
    })

    it('validateURL accepts http(s) urls and rejects plain words', () => {
      expect(validateURL('https://www.northeastern.edu')).toBe(true)
      expect(validateURL('not a url')).toBe(false)
    })

    it('case validators behave as in Vue 2', () => {
      expect(validateLowerCase('abc')).toBe(true)
      expect(validateLowerCase('Abc')).toBe(false)
      expect(validateUpperCase('ABC')).toBe(true)
      expect(validateUpperCase('aBC')).toBe(false)
      expect(validatAlphabets('aBC')).toBe(true)
      expect(validatAlphabets('aB1')).toBe(false)
    })
  })

  describe('statusFilter (was a Vue 2 template filter)', () => {
    it('DISP-14: maps published/draft/deleted to the Element tag types', () => {
      expect(statusFilter('published')).toBe('success')
      expect(statusFilter('draft')).toBe('gray')
      expect(statusFilter('deleted')).toBe('danger')
    })

    it('returns undefined for an unknown status, as the Vue 2 filter did', () => {
      expect(statusFilter('whatever')).toBeUndefined()
    })
  })

  describe('shared helpers', () => {
    it('DISP-03: isExternal recognises external links', () => {
      expect(isExternal('https://example.com')).toBe(true)
      expect(isExternal('mailto:a@b.c')).toBe(true)
      expect(isExternal('/campusset/list')).toBe(false)
    })

    it('parseTime formats a date with the default pattern', () => {
      expect(parseTime(new Date('2022-05-03T17:14:11'))).toBe('2022-05-03 17:14:11')
    })

    it('parseTime returns null without arguments', () => {
      expect(parseTime()).toBeNull()
    })

    it('formatTime reports a recent timestamp as "Just Now"', () => {
      expect(formatTime(Math.floor(Date.now() / 1000))).toBe('Just Now')
    })
  })

  describe('token helpers', () => {
    it('AUTH-01: the token is stored in the Admin-Token cookie', () => {
      setToken('abc123')
      expect(getToken()).toBe('abc123')
      removeToken()
      expect(getToken()).toBeUndefined()
    })
  })
})
