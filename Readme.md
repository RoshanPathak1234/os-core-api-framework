# os core api framework

This project implements a comprehensive REST API framework for simulating and managing key Operating System algorithms, including CPU scheduling, memory management, disk scheduling, and more. The system provides endpoints for interacting with these algorithms, enabling users to simulate and manage different OS functionalities and performance metrics.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Example](#example)
- [API Endpoints](#api-endpoints)
    - [CPU Scheduling](#cpu-scheduling)
    - [Memory Management](#memory-management)
    - [Disk Scheduling](#disk-scheduling)
- [License](#license)

## Project Overview

This project provides a platform to simulate a wide range of operating system functionalities, focusing on key areas such as:

- **CPU Scheduling Algorithms**: Implementations for various CPU scheduling strategies like First-Come-First-Served (FCFS), Shortest Job First (SJF), Priority Scheduling, etc.
- **Memory Management**: Simulation of memory management algorithms like paging, segmentation, and virtual memory.
- **Disk Scheduling**: Implementation of disk scheduling algorithms like First-Come-First-Served (FCFS), Shortest Seek Time First (SSTF), and SCAN algorithms.
- **Process Management**: Manages process creation, scheduling, and various process performance metrics like turnaround time, waiting time, etc.

## Features

- **CPU Scheduling Algorithms**: Supports FCFS, SJF, Priority Scheduling, and more.
- **Memory Management**: Includes algorithms for paging, segmentation, and virtual memory management.
- **Disk Scheduling**: Implements disk scheduling strategies like FCFS, SSTF, SCAN, and others.
- **Performance Metrics**: Calculates turnaround time, waiting time, and other key metrics for processes.
- **Modular Architecture**: Easily extensible to add more OS algorithms in the future.

## Technologies Used

- **Java**: Core language for implementing the algorithms.
- **Spring Boot**: Framework for building the REST API.
- **Swagger**: For documenting the API.
- **Lombok**: Reduces boilerplate code in model classes.
- **JUnit**: For unit testing.
- **H2 Database**: (Optional) In-memory database for storing process and algorithm data (if required).

## Installation

### Prerequisites

- JDK 11 or later.
- Maven (or IDE with Maven support).

### Steps to Install

1. Clone the repository:
    ```bash
    git clone https://github.com/RoshanPathak1234/os-core-api-framework.git
    ```

2. Navigate to the project folder:
    ```bash
    cd os-algorithms-rest-api
    ```

3. Build the project using Maven:
    ```bash
    mvn clean install
    ```

4. Run the Spring Boot application:
    ```bash
    mvn spring-boot:run
    ```

The application should now be running at `http://localhost:8080`.

## Usage

### API Endpoints

The API exposes various endpoints for interacting with the algorithms.

#### CPU Scheduling

- **GET /os-core-api/cpu/getAllSchedulingStrategy**  
  Retrieves all available CPU scheduling strategies.

- **POST /os-core-api/cpu/scheduler/compute**  
  Executes a selected scheduling algorithm. Send a JSON request with the following fields:

    - `arrivalTime`: List of integers representing the arrival time of each process.
    - `burstTime`: List of integers representing the burst time of each process.
    - `priority`: List of integers representing the priority of each process.
    - `contextSwitchingDelay`: Integer representing the context switching delay.
    - `strategy`: The scheduling strategy to use (e.g., "FCFS", "SJF", "Priority").

#### Memory Management

- **POST /os-core-api/memory/allocate**  
  Allocates memory to processes based on the chosen memory management strategy (paging, segmentation, etc.).

- **GET /os-core-api/memory/getAllocatedMemory**  
  Retrieves the current memory allocation status of all processes.

#### Disk Scheduling

- **POST /os-core-api/disk/schedule**  
  Schedules disk I/O requests using algorithms like FCFS, SSTF, or SCAN.

- **GET /os-core-api/disk/getDiskRequests**  
  Retrieves the current disk I/O request queue and their statuses.

## Example

### CPU Scheduling Example

To execute a CPU scheduling algorithm, you can make a POST request to `http://localhost:8080/os-core-api/cpu/compute` with the following example request body:

```json
{
  "arrivalTime": [0, 2, 4, 6],
  "burstTime": [5, 3, 1, 2],
  "priority": [1, 3, 2, 4],
  "contextSwitchingDelay": 0,
  "strategy": "FCFS"
}
