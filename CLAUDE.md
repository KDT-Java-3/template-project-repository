# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Purpose

This is a template repository for Sparta coding bootcamp weekly project submissions. It serves as a starting point where students fork/clone and work on their assignments following a specific Git workflow.

## Git Workflow

This repository follows a strict branching and submission workflow:

### Branch Naming Conventions

- **Work branches**: `work/{고유번호}-{영문 이름}`
  - Example: `work/1234-john-doe`
  - Used for active development work

- **Submission branches**: `project/{고유번호}-{영문 이름}`
  - Example: `project/1234-john-doe`
  - Target branch for pull requests when submitting work

### Submission Process

1. Create a work branch from the repository
2. Commit and push changes to the work branch
3. Create a PR from work branch → submission branch (project/*)
4. Merge the PR to the submission branch when ready

## Pull Request Guidelines

When creating PRs, follow the template in `.github/pull_request_template.md`:

- **Title format**: `[N주차] JAVA_XX_커머스: 상품/주문_이름_프로젝트`
  - Example: `[1주차] JAVA_01_커머스: 상품/주문_김르탄_프로젝트`

- **Required sections**:
  - 📝 작업 내용 (Work completed): List all implemented features/APIs
  - 🔒 고민이 되었던 부분 (Challenges): Optional - describe problems encountered and solutions
  - 💬 리뷰 요구사항 (Review requests): Optional - specific areas for reviewer feedback

## Important Notes for Claude Code

- The main branch is `main`
- Current working branch follows the pattern `work/{id}-{name}`
- When creating PRs, target the corresponding `project/{id}-{name}` branch, not `main`
- This is a template repository - actual project code will be added by students based on weekly assignments
